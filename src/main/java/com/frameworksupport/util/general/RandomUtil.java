package com.frameworksupport.util.general;


import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public final class RandomUtil {
  public static final int MY_DAYS = 365;
  
  public static final int MY_INT = 730;
  
  private static String[] words = new String[] { 
      "abcd", 
      "posst", 
      "wnko", 
      "fsnh", 
      "asd", 
      "cvag", 
      "bgty", 
      "ghys", 
      "med", 
      "reg", 
      "ght", 
      "rtgh", 
      "lyop", 
      "inn", 
      "utr", 
      "lab", 
      "etp", 
      "doel", 
      "mag", 
      "aiuam", 
      "wedut", 
      "seeut", 
      "dkl", 
      "vpqt", 
      "at", 
      "vier", 
      "eisd", 
      "etcp", 
      "accu", 
      "etsp", 
      "juss", 
      "duspia", 
      "dosli", 
      "etpa", 
      "ears", 
      "rebm", 
      "sxtsa", 
      "clxt", 
      "kpa", 
      "guu", 
      "nogxi", 
      "saea", 
      "tma", 
      "snxcct", 
      "esg" };
  
  private static String[] number = new String[] { 
      "12", "34", "25", "78", "90", "10", "31", "43", "52", "87", 
      "09", "56", "16", 
      "26", "76", "31", "35", "37", "54", "56", 
      "58", "69", "94", "23", "45", "47", "49", "13", "15", "17", 
      "19", 
      "89", "86", "82", "51", "57", "59", "29", "24", "42", 
      "48", "53", "43", "33", "34", "39", "22", "27", "87", 
      "94", 
      "91", "63", "65", "60", "40", "94", "81", "11", "73", "71" };
  
  private static Random random = new Random();
  
  public static synchronized String getRandomWord() {
    return words[random.nextInt(words.length)];
  }
  
  public static synchronized String getRandomEmail() {
    return String.valueOf(getRandomWord()) + getRandomNumber(7) + "auto@" + "yopmail" + ".com";
  }
  
  public static synchronized String getRandomNumber() {
    return number[random.nextInt(number.length)];
  }
  
  public static synchronized String getRandomPhone() {
    return "43529" + getRandomNumber(5);
  }
  
  public static synchronized String getStaticPhone() {
    return "5005550006";
  }
  
  public static synchronized String getRandomWebSite() {
    return "www." + getRandomWord() + getRandomWord() + getRandomWord() + ".com";
  }
  
  public static synchronized Date getRandomDate() {
    Calendar calendar = Calendar.getInstance();
    calendar.add(5, 365 - random.nextInt(730));
    return calendar.getTime();
  }
  
  public static synchronized String getRandomName() {
    return String.valueOf(getRandomWord()) + getRandomWord() + getRandomWord();
  }
  
  public static synchronized long getRandomNumber(int digCount) {
    if (digCount <= 0 || digCount > 18)
      throw new IllegalArgumentException(
          "A long can store the random of 18 full digits, you required: " + digCount); 
    Random r = new Random();
    long randomNumber = (r.nextInt(9) + 1);
    for (int i = 1; i < digCount; i++)
      randomNumber = randomNumber * 10L + r.nextInt(10); 
    return randomNumber;
  }
  
  public static long getRandomNumber(long Min, long Max) {
    return (long)(Math.random() * (Max - Min)) + Min;
  }
  
  public static String getBirthday(UserType userType) {
    String month = String.valueOf(getRandomNumber(1L, 12L));
    if (month.length() == 1)
      month = String.format("%02d", new Object[] { Integer.valueOf(Integer.parseInt(month)) }); 
    String day = String.valueOf(getRandomNumber(1L, 28L));
    if (day.length() == 1)
      day = String.format("%02d", new Object[] { Integer.valueOf(Integer.parseInt(day)) }); 
    if (userType == UserType.MINOR)
      return String.valueOf(month) + day + String.valueOf(getRandomNumber(2003L, 2019L)); 
    if (userType == UserType.PRIMARY)
      return String.valueOf(month) + day + String.valueOf(getRandomNumber(1945L, 1999L)); 
    return String.valueOf(month) + day + String.valueOf(getRandomNumber(1945L, 1999L));
  }
  
  public enum Age {
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    FIFTEEN(15),
    SIXTEEN(16),
    SEVENTEEN(17),
    EIGHTEEN(18),
    NINETEEN(19);
    
    private int ageInNumbers;
    
    public int getAgeInNumber() {
      return this.ageInNumbers;
    }
    
    Age(int ageInNumbers) {
      this.ageInNumbers = ageInNumbers;
    }
  }
  
  public enum UserType {
    MINOR, PRIMARY, DEPENDENT;
  }
  
  public static String createPassword(int len) {
    System.out.println("Generating password using random() : ");
    System.out.println("Your new password is : ");
    String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String Small_chars = "abcdefghijklmnopqrstuvwxyz";
    String numbers = "0123456789";
    String symbols = "!@#$%";
    String values = String.valueOf(Capital_chars) + Small_chars + numbers + symbols;
    Random rndm_method = new Random();
    char[] password = new char[len];
    for (int i = 0; i < len - 4; i++)
      password[i] = values.charAt(rndm_method.nextInt(values.length() - 1)); 
    return "Test123!";
  }
  
  public static synchronized String getRandomIpAddress() {
    Random r = new Random();
    return String.valueOf(r.nextInt(256)) + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);
  }
}
