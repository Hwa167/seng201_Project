package com.dd.playgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Battle {

    public static boolean battleTeam(Team team1) {

        //检查队伍是否符合规则
        ArrayList<PlayerRole> absentRoles = new ArrayList<>();
        for (PlayerRole value : PlayerRole.values()) {
            boolean exists = team1.players.stream().anyMatch(item -> item.role == value);
            if (!exists) {
                absentRoles.add(value);
            }
        }
        if (!absentRoles.isEmpty()) {
            System.out.println(team1.name + " 团队中缺少类型"+absentRoles+"运动员,去市场逛逛吧!");
            return false;
        }

        boolean checkEndurance = team1.players.stream().filter(item -> item.endurance >0 ).count() == 5;
        if (!checkEndurance) {
            System.out.println(team1.name + " 队伍中有队员耐力不足,不能参与比赛!");
            return false;
        }

        Team systemTeam = SystemUtils.getTeam();

        System.out.println("比赛开始");
        List<BasketballPlayer> playerList1 = team1.players;
        List<BasketballPlayer> playerList2 = systemTeam.players;

        int leftScore = 0, rightScore = 0;
        for (PlayerRole value : PlayerRole.values()) {
            int battledPlayer = battlePlayer(
                    playerList1.stream().filter(item -> item.role == value).findFirst(),
                    playerList2.stream().filter(item -> item.role == value).findFirst()
            );
            if (battledPlayer > 0) {
                ++leftScore;
            } else if (battledPlayer < 0) {
                ++rightScore;
            } else {
                ++leftScore;
                ++rightScore;
            }
        }
        if (leftScore > rightScore) {
            System.out.println("恭喜您赢得了此次比赛,获得奖金" + BasketBallConfig.BONUS + ", 积分" + BasketBallConfig.GAME_WIN + "...");
            team1.addIntegral(BasketBallConfig.GAME_WIN);
            team1.addBonus(BasketBallConfig.BONUS);
        } else if (leftScore < rightScore) {
            System.out.println("您输掉了此场比赛, 获得积分" + BasketBallConfig.GAME_LOSE + ", 再接再厉...");
            team1.addIntegral(BasketBallConfig.GAME_LOSE);
        } else {
            System.out.println("此次比赛双方实力都很强劲...");
            team1.addIntegral(BasketBallConfig.GAME_DRAW);
        }
        return true;
    }

    private static int battlePlayer(Optional<BasketballPlayer> player1, Optional<BasketballPlayer> player2) {
        if (player1.isPresent() && player2.isPresent()) {
            //产生对抗,增加疲劳度
            BasketballPlayer tempPlayer1 = player1.get();
            BasketballPlayer tempPlayer2 = player2.get();

            System.out.println("我方 <" + tempPlayer1.name + "> 与对方 <" + tempPlayer2.name + "> 较量中...");

            int winStrength = battleCapacity(tempPlayer1.currentStrength, tempPlayer2.currentStrength);
            int winDefense = battleCapacity(tempPlayer1.currentDefense, tempPlayer2.currentDefense);

            int tempCount = winStrength + winDefense;

            if (tempCount > 0) {
                System.out.println("我方 <" + tempPlayer1.name + "> 取得了胜利...");
            } else if (tempCount < 0) {
                System.out.println("对方 <" + tempPlayer2.name + "> 取得了胜利...");
            } else {
                System.out.println("此次较量双方战成平手...");
            }

            double leftEndurance = BasketBallConfig.calculateDecline(tempCount);
            double rightEndurance = BasketBallConfig.calculateDecline(tempCount * -1);

            tempPlayer1.declineEndurance(leftEndurance);
            tempPlayer2.declineEndurance(rightEndurance);

            return Integer.compare(tempCount, 0);
        }
        return 0;
    }

    private static int battleCapacity(double left, double right) {
        return Double.compare(left, right);
    }
}
