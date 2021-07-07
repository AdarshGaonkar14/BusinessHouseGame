package com.coding.business.house.game.startup;

import java.util.List;

import com.coding.business.house.game.enums.BoardCellType;
import com.coding.business.house.game.enums.HotelType;
import com.coding.business.house.game.exception.InsufficientFundsException;
import com.coding.business.house.game.model.BoardCell;
import com.coding.business.house.game.model.GameBoard;
import com.coding.business.house.game.model.Hotel;
import com.coding.business.house.game.model.Player;
import com.coding.business.house.game.service.GameService;
import com.coding.business.house.game.service.impl.GameServiceImpl;

public class PlayGame {

	static GameService gameService = new GameServiceImpl();

	public static void main(String[] args) {

		GameBoard gameBoard = gameService.setupGameBoard(10, 5000);
		List<BoardCell> cells = gameBoard.getCells();
		List<Player> players = gameService.setupPlayers(3);

		for (int turn = 1; turn <= 7; turn++) {
			for (Player player : players) {
				int currentLocation = player.getCurrentCellLocation();
				int diceNum = gameService.playDice(turn, player.getPlayerId());
				System.out.println(player.getPlayerName() + " is at " + currentLocation + " diceNum : " + diceNum);
				int targetCellIndex = currentLocation + diceNum;

				if (targetCellIndex > 9) {
					targetCellIndex = targetCellIndex - 9;
				}

				System.out.println("Target cell is " + targetCellIndex);
				player.setCurrentCellLocation(targetCellIndex);

				BoardCell targetCell = cells.get(targetCellIndex);
				processCellOperation(player, gameBoard, targetCell);

			}

		}

		players.forEach(player -> {
			int assetAmount = getAssetAmount(player.getHotels());
			System.out.println(player.getPlayerName() + " has total money " + player.getAmount()
					+ " and asset of amount " + assetAmount);
		});
		System.out.println("Balance at bank : " + gameBoard.getBankMoney());
	}

	private static int getAssetAmount(List<Hotel> hotels) {
		int amount = 0;
		if (!hotels.isEmpty()) {
			for (Hotel hotel : hotels) {
				amount = amount + hotel.getHotelType().getValue();

			}
		}
		return amount;
	}

	private static void processCellOperation(Player player, GameBoard gameBoard, BoardCell targetCell) {

		switch (targetCell.getCellType()) {
		case J:
			player.setAmount(player.getAmount() - BoardCellType.J.getValue());
			gameService.depositAmoutToBank(gameBoard, BoardCellType.J.getValue());
			break;
		case H:
			Hotel hotel = targetCell.getHotel();
			Player hotelOwner = hotel.getHotelOwner();
			HotelType hotelType = hotel.getHotelType();
			if (null != hotelOwner) {
				if (player.getPlayerId() == hotel.getHotelOwner().getPlayerId()) {
					upgradeHotel(player, gameBoard, hotel, hotelType);
				} else {
					payRent(player, hotelOwner, hotelType);
				}
			} else {
				buyHotel(player, gameBoard, hotel, hotelType);
			}

			break;
		case L:
			gameService.withDrawAmountFromBank(gameBoard, BoardCellType.L.getValue());
			player.setAmount(player.getAmount() + BoardCellType.L.getValue());
			break;
		case E:
			break;
		default:
			break;
		}

	}

	private static void buyHotel(Player player, GameBoard gameBoard, Hotel hotel, HotelType hotelType) {
		if (player.getAmount() >= hotelType.getValue()) {
			gameService.depositAmoutToBank(gameBoard, hotelType.getValue());
			player.setAmount(player.getAmount() - hotelType.getValue());
			hotel.setHotelOwner(player);
			player.getHotels().add(hotel);
		} else {
			throw new InsufficientFundsException("You don't have enough money to buy this hotel");
		}
	}

	private static void payRent(Player player, Player hotelOwner, HotelType hotelType) {
		// owned by some other player CASE : D (Pay Rent)
		if (player.getAmount() >= hotelType.getRent()) {
			hotelOwner.setAmount(hotelOwner.getAmount() + hotelType.getRent());
			player.setAmount(player.getAmount() - hotelType.getRent());
		} else {
			throw new InsufficientFundsException("You don't have enough money to pay rent");

		}
	}

	private static void upgradeHotel(Player player, GameBoard gameBoard, Hotel hotel, HotelType hotelType) {
		if (HotelType.PLATINUM != hotel.getHotelType()) {
			HotelType upgradeToType = getNextUpgrade(hotel.getHotelType());
			int deltaToPay = upgradeToType.getValue() - hotel.getHotelType().getValue();
			player.setAmount(player.getAmount() - deltaToPay);
			gameService.depositAmoutToBank(gameBoard, deltaToPay);
		} else {
			System.out.println("Platinum can't be upgraded");
		}
	}

	private static HotelType getNextUpgrade(HotelType hotelType) {
		switch (hotelType) {
		case SILVER:
			return HotelType.GOLD;
		case GOLD:
			return HotelType.PLATINUM;
		default:
			break;
		}
		return null;
	}

}
