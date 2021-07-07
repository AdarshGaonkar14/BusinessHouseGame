package com.coding.business.house.game.service;

import java.util.List;

import com.coding.business.house.game.model.GameBoard;
import com.coding.business.house.game.model.Player;

public interface GameService {

	public GameBoard setupGameBoard(int numberOfCells, int initialMoney);

	public List<Player> setupPlayers(int numberOfPlayer);

	public int playDice(int turn, int playerId);

	public void depositAmoutToBank(GameBoard gameBoard, int amountToDeposit);

	public void withDrawAmountFromBank(GameBoard gameBoard, int amountToWithdraw);
}
