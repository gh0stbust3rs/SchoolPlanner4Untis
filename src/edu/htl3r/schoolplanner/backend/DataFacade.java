/* SchoolPlanner4Untis - Android app to manage your Untis timetable
    Copyright (C) 2011  Mathias Kub <mail@makubi.at>
			Sebastian Chlan <sebastian@schoolplanner.at>
			Christian Pascher <christian@schoolplanner.at>
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package edu.htl3r.schoolplanner.backend;

/**
 * Diese Klasse wird verwendet, um Datenabfragen aus unsicheren Quellen zu behandeln.<br>
 * Sie dient als Schnittstelle zu den Daten, um im Fehlerfall ueber einen Error-Code diesen bekannt zu geben.
 * @param <E> Art der Daten, die abgerufen werden
 */
public class DataFacade<E> {

	private boolean successful = false;
	
	private E data;
	private int errorCode = 0;
	
	
	/**
	 * Diese Methode gibt 'true' zurueck, wenn die Daten erfolgreich abgefragt werden konnten.<br>
	 * Sie sollte aufgerufen werden, bevor per {@link #getData()} die Daten abgerufen werden, um sicherzustellen, dass Daten problemlos abgefragt werden konnten.
	 * @return 'true', wenn der Datenabruf erfolgreich war
	 */
	public boolean isSuccessful() {
		return successful;
	}
	
	/**
	 * Legt fest, ob der Datenabruf erfolgreich war oder nicht.<br>
	 * Kann verwendet werden, um im nach dem Setzen der Daten oder dem Error-Code, den Rueckgabewert von {@link #isSuccessful()} zu manipulieren.
	 * @param successful 'true', wenn die Datenabfrage als erfolgreich gesetzt werden soll
	 */
	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
	
	/**
	 * Liefert die Daten zur vorher gestellten Abfrage, falls Daten abgerufen werden konnten.<br>
	 * Kann nur verwendet werden, wenn die Datenabfrage vorher erfolgreich war, daher sollte vorher mit {@link #isSuccessful()} ueberprueft werden, ob dies auch zutrifft, ansonsten wird eine {@link IllegalStateException} geworfen.
	 * @return Die Daten zur erfolgreichen Abfrage
	 */
	public E getData() {
		if(!successful)
			throw new IllegalStateException("Unable to access data, the previous request was unsuccessful");
		return data;
	}
	
	/**
	 * Setzt die Daten der Abfrage und den Rueckgabewert fuer {@link #isSuccessful()} auf 'true'.
	 * @param data Daten, die gesetzt werden sollen
	 */
	public void setData(E data) {
		this.data = data;
		successful = true;
	}
	
	/**
	 * Liefert den Error-Code zur vorher gestellten Abfrage, falls ein Fehler aufgetreten ist.<br>
	 * Kann nur verwendet werden, wenn die Datenabfrage vorher erfolglos war, daher sollte vorher mit {@link #isSuccessful()} ueberprueft werden, ob dies auch zutrifft, ansonsten wird eine {@link IllegalStateException} geworfen.
	 * @return Den Error-Code zur fehlgeschlagenen Abfrage
	 */
	public int getErrorCode() {
		if(successful)
			throw new IllegalStateException("No error code provided, the previous request was successful");
		return errorCode;
	}
	
	/**
	 * Setzt den Error-Code der Abfrage und den Rueckgabewert fuer {@link #isSuccessful()} auf 'false'.
	 * @param errorCode Error-Code, der gesetzt werden sollen
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
		successful = false;
	}
	
	
}
