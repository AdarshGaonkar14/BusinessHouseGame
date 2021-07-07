package com.coding.business.house.game.model;

import com.coding.business.house.game.enums.HotelType;

public class Hotel {

	private int hotelId;
	private String hotelName;
	private Player hotelOwner;
	private HotelType hotelType;

	public Hotel(int hotelId, String hotelName, Player hotelOwner, HotelType hotelType) {
		super();
		this.hotelId = hotelId;
		this.hotelName = hotelName;
		this.hotelOwner = hotelOwner;
		this.hotelType = hotelType;
	}

	public int getHotelId() {
		return hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public Player getHotelOwner() {
		return hotelOwner;
	}

	public void setHotelOwner(Player hotelOwner) {
		this.hotelOwner = hotelOwner;
	}

	public HotelType getHotelType() {
		return hotelType;
	}

	public void setHotelType(HotelType hotelType) {
		this.hotelType = hotelType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hotelId;
		result = prime * result + ((hotelName == null) ? 0 : hotelName.hashCode());
		result = prime * result + ((hotelOwner == null) ? 0 : hotelOwner.hashCode());
		result = prime * result + ((hotelType == null) ? 0 : hotelType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hotel other = (Hotel) obj;
		if (hotelId != other.hotelId)
			return false;
		if (hotelName == null) {
			if (other.hotelName != null)
				return false;
		} else if (!hotelName.equals(other.hotelName))
			return false;
		if (hotelOwner == null) {
			if (other.hotelOwner != null)
				return false;
		} else if (!hotelOwner.equals(other.hotelOwner))
			return false;
		if (hotelType != other.hotelType)
			return false;
		return true;
	}

}
