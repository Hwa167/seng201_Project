package com.dd.playgame.application;

import com.dd.playgame.bean.GameInfo;

import java.io.*;

/**
 * Responsible for game archiving and archive reading
 */
public class DataHandler {

    private static final File savefile =
            new File(new File("").getAbsolutePath(), "playgame.dat");

    /**
     * Check if there is archived data
     *
     * @return
     */
    public static boolean checkSaved() {
        return savefile.exists();
    }

    /**
     * Clear archived data
     *
     * @return
     */
    public static boolean deleteData() {
        if (checkSaved()) {
            return savefile.delete();
        }
        return true;
    }

    /**
     * Save current game data
     */
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

    /**
     * Read game save data
     */
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
