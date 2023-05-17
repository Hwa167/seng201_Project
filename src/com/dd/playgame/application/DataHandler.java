package com.dd.playgame.application;

import java.io.*;

public class DataHandler {

    private static final File savefile =
            new File(new File("").getAbsolutePath(), "playgame.dat");

    public static boolean checkSaved() {
        return savefile.exists();
    }

    public static boolean deleteData() {
        if (checkSaved()) {
            return savefile.delete();
        }
        return true;
    }

    public static void saveData() {
        try {
            if (!savefile.exists()) {
                savefile.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(savefile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(PlayerGameData.getGameInfo());
            oos.flush();
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadData() {
        if (!savefile.exists()) {
            return;
        }
        try {
            FileInputStream fis = new FileInputStream(savefile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            GameInfo gameInfo = (GameInfo) ois.readObject();
            PlayerGameData.setGameInfo(gameInfo);
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
