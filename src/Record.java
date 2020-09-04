/* Program Name: Flappy Doge (FST)
   File Name: Record.java
   Author: Ethan Ohayon
   Date Began: March 15, 2017
   Date Due: June 2, 2017
   Purpose: Creates a Record object which consists of the player name, score, flaps, medal and date
*/

import java.io.*;
import java.lang.Comparable;

public class Record implements Serializable, Comparable <Record> {

 String name, medal, date;
 int score, flaps;

 public Record() {
	this(null, 0, 0, null, null);
 }

 public Record(String name, int score, int flaps, String medal, String date) {
	this.name = name;
	this.medal = medal;
	this.date = date;
	this.score = score;
	this.flaps = flaps;
 }

 public void setName(String name) {
	this.name = name;
 }

 public void setMedal(String medal) {
	this.medal = medal;
 }

 public void setScore(int score) {
	this.score = score;
 }

 public void setDate(String date) {
	this.date = date;
 }

 public void setJumpCount(int jumpCount) {
	this.flaps = flaps;
 }

 public String getName() {
	return this.name;
 }

 public String getMedal() {
	return this.medal;
 }

 public int getScore() {
	return this.score;
 }

 public String getDate() {
	return this.date;
 }

 public int getFlaps() {
	return this.flaps;
 }

 public String toString() {
	return name + " " + String.valueOf(score) + " " + String.valueOf(flaps) + " " + medal + " " + date;
 }

 public int compareTo(Record o) {
	if (o.getScore() < this.getScore()) {
	 return -1;
	} else if (o.getScore() > this.getScore()) {
	 return 1;
	} else {
	 if (o.getFlaps() > this.getFlaps())
		return -1;
	 else if (o.getFlaps() < this.getFlaps())
		return 1;
	 else
		return 0;
	}
 }

 public boolean contains(String parameter) {
	if (name.contains(parameter) || medal.contains(parameter) || date.contains(parameter) || Integer.toString(score).contains(parameter) || Integer.toString(flaps).contains(parameter)) {
	 return true;
	} else {
	 return false;
	}
 }

 public Record toUpperCase() {
	return new Record(this.name.toUpperCase(), score, flaps, this.medal.toUpperCase(), this.date.toUpperCase());
 }
}