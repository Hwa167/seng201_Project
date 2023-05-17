package com.dd.playgame.command;

import com.dd.playgame.application.PlayerGameData;
import com.dd.playgame.bean.*;
import com.dd.playgame.generator.TeamGenerator;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        //球队名称
        System.out.println("input team name");
        String teamName = scanner.nextLine();

        //持续周期
        System.out.println("input cycle");
        int cycle = scanner.nextInt();

        //难度选择
        System.out.println("input difficulty(0:easy/1:normal/2:hard)");
        Difficulty difficulty = Difficulty.EASY;
        switch (scanner.nextInt()) {
            case 1:
                difficulty = Difficulty.NORMAL;
                break;
            case 2:
                difficulty = Difficulty.HARD;
                break;
            default:
                break;
        }
        PlayerGameData.initConfig(difficulty, teamName, cycle);

        GameInfo gameInfo = PlayerGameData.getGameInfo();
        Team userTeam = gameInfo.team;

        System.out.println(String.format("created team %s success! difficulty: %s init amount: %s"
                , teamName, difficulty, userTeam.initAmount));

        boolean exit = false;
        while (!exit && gameInfo.cycle <= gameInfo.allCycle) {
            System.out.println("Go On" + gameInfo.allCycle + "Weeks, The" + gameInfo.cycle + "Weeks.");
            System.out.println("Next step:(1/Look At Club,2/Studium,3/Randmon Match,4/Look Around Shop,5/Next Week,exit/Exit Game)");
            String chose = scanner.next();
            switch (chose) {
                case "1":
                    teamOperation(scanner, userTeam);
                    break;
                case "2":
                    stadiumOperation(scanner, gameInfo);
                    break;
                case "3":
                    boolean success = Battle.battleTeam(userTeam, null, true);
                    if (success) {
                        showTeamInfo(userTeam);
                    }
                    break;
                case "4":
                    marketOperation(scanner, userTeam, gameInfo.market);
                    break;
                case "5":
                    PlayerGameData.nextWeek();
                    break;
                case "exit":
                    exit = true;
                    break;
                default:
                    break;
            }
        }

        if (!exit) {
            System.out.println("Match Over!");
            //正常结束,展示赛季成绩
            showTeamInfo(userTeam);
        }
    }

    private static void stadiumOperation(Scanner scanner, GameInfo gameInfo) {
        System.out.println("System Club:");
        if (gameInfo.systemTeams.isEmpty()) {
            gameInfo.systemTeams = TeamGenerator.generateTeams(gameInfo);
        }
        List<Team> systemTeams = gameInfo.systemTeams;
        for (int i = 0; i < systemTeams.size(); i++) {
            System.out.println(String.format("%-4s Team: %-20s  Player Comprehensive score:%-8s"
                    , i, systemTeams.get(i).name, systemTeams.get(i).calculateTeamScore()));
        }
        System.out.println("Please input the number:(exit/Exit The Operation)");
        boolean exit = false;
        while (!exit) {
            int num = scanner.nextInt();
            if (num >= systemTeams.size()) {
                System.out.println("The number error,please input again!");
            } else {
                exit = true;
                boolean battled = Battle.battleTeam(gameInfo.team, systemTeams.get(num), false);
                if (battled) {
                    showTeamInfo(gameInfo.team);
                }
            }
        }
    }

    private static void marketOperation(Scanner scanner, Team myTeam, Market market) {

        boolean buy = true;
        while (buy) {
            for (MarketPlayer player : market.players) {
                System.out.println(String.format("Player%-9s %-4s %-13s strength:%-11s defense:%-11s role:%-23s price:%-11s", "",
                        player.num, player.name, player.getStrengthStr(),
                        player.getDefenseStr(), player.role.value, player.getPriceStr()));
            }
            for (MarketConsumable consumable : market.consumables) {
                System.out.println(String.format("Consumable%-5s %-4s %-24s effect:%-11s price:%-11s", "",
                        consumable.num, consumable.name, consumable.getEffectStr(), consumable.getPriceStr()));
            }

            System.out.println("Please enter the purchase item number:(exit/Exit the shop)");
            boolean fullPrice = true;
            while (fullPrice) {

                String input = scanner.next();
                int num = 0;
                if ("exit".equalsIgnoreCase(input)) {
                    num = -1;
                } else {
                    try {
                        num = Integer.parseInt(input);
                    } catch (NumberFormatException ignored) {
                        num = -1;
                    }
                }

                if (num == -1) {
                    fullPrice = false;
                    buy = false;
                    continue;
                }

                final int inputNum = num;
                Optional<MarketPlayer> findPlayer = market.players.stream().filter(item -> item.num == inputNum).findFirst();
                if (findPlayer.isPresent()) {
                    MarketPlayer player = findPlayer.get();
                    if (myTeam.amount >= player.price) {
                        System.out.println("Please choose to join the team or substitute: (1/Join,2/Substitute)");
                        market.players.remove(player);
                        String choseStr = scanner.next();
                        int chose = 0;
                        try {
                            chose = Integer.parseInt(choseStr);
                        } catch (NumberFormatException ignored) {
                        }
                        if (chose == 1) {
                            Optional<Player> first = myTeam.players.stream().filter(item -> item.role == player.role).findFirst();
                            if (first.isPresent()) {
                                Player it = first.get();
                                myTeam.players.remove(it);
                                myTeam.reserves.add(it);
                                myTeam.players.add(player);
                            } else {
                                myTeam.players.add(player);
                            }
                        } else {
                            myTeam.reserves.add(player);
                        }
                        myTeam.amount = myTeam.amount - player.price;
                        System.out.println("Purchase successful, current balance(" + myTeam.getAmountStr() + "),Whether to continue(y/n)?");
                        buy = "y".equalsIgnoreCase(scanner.next());
                        fullPrice = false;
                    } else {
                        System.out.println("underfund!");
                    }
                } else {
                    Optional<MarketConsumable> findConsumable = market.consumables.stream().filter(item -> item.num == inputNum).findFirst();
                    if (findConsumable.isPresent()) {
                        MarketConsumable consumable = findConsumable.get();
                        if (myTeam.amount >= consumable.price) {
                            market.consumables.remove(consumable);
                            myTeam.consumables.add(consumable);
                            myTeam.amount = myTeam.amount - consumable.price;
                            System.out.println("Purchase successful, current balance(" + myTeam.getAmountStr() + "),Whether to continue(y/n)?");
                            buy = "y".equalsIgnoreCase(scanner.next());
                            fullPrice = false;
                        } else {
                            System.out.println("underfund!");
                        }
                    } else {
                        System.out.println("No look for the number:" + inputNum + "item!");
                    }
                }
            }
        }
    }

    private static void teamOperation(Scanner scanner, Team team) {
        showTeamInfo(team);
        boolean operation = true;
        while (operation) {
            System.out.println("Please select the actions you need :(1/ swap a player,2/ Add a player,3/ remove a player,4/ Use consumables,exit/ Exit)");
            String index = scanner.next();
            switch (index) {
                case "1": {
                    boolean change = true;
                    while (change) {
                        try {
                            System.out.println("Please select a team member number:");
                            final int playerNum = scanner.nextInt();
                            System.out.println("Please select the member number in the backup team:");
                            final int reserveNum = scanner.nextInt();
                            Player activityPlayer = team.players.stream().filter(item -> item.num == playerNum).findFirst().get();
                            Player reservePlayer = team.reserves.stream().filter(item -> item.num == reserveNum).findFirst().get();
                            if (activityPlayer.role != reservePlayer.role) {
                                System.out.println("The player roles that need to be exchanged do not match, please reselect!");
                            } else if (reservePlayer.endurance <= 0d) {
                                System.out.println("The substitute has no strength to play again, please choose again!");
                            } else {
                                team.players.remove(activityPlayer);
                                team.players.add(reservePlayer);

                                team.reserves.remove(reservePlayer);
                                team.reserves.add(activityPlayer);
                                System.out.println("Exchange success");
                                showTeamInfo(team);
                                change = false;
                                operation = false;
                            }
                        } catch (Exception e) {
                            scanner.next();
                            change = false;
                            operation = false;
                        }
                    }
                }
                break;
                case "2": {
                    boolean change = true;
                    while (change) {
                        try {
                            System.out.println("Please select the replacement team member number:");
                            final int reserveNum = scanner.nextInt();
                            Player reservePlayer = team.reserves.stream().filter(item -> item.num == reserveNum).findFirst().get();

                            if (reservePlayer.endurance <= 0d) {
                                System.out.println("The substitute has no strength to play again, please choose again!");
                            } else {
                                Optional<Player> activityPlayer = team.players.stream().filter(item -> item.role == reservePlayer.role)
                                        .findFirst();
                                activityPlayer.ifPresent(itemPlayer -> {
                                    team.players.remove(itemPlayer);
                                    team.reserves.add(itemPlayer);
                                });
                                team.players.add(reservePlayer);
                                team.reserves.remove(reservePlayer);
                                System.out.println("Add a team successfully");
                                showTeamInfo(team);
                                change = false;
                                operation = false;
                            }
                        } catch (Exception e) {
                            scanner.next();
                            change = false;
                            operation = false;
                        }
                    }
                }
                break;
                case "3": {
                    boolean change = true;
                    while (change) {
                        try {
                            System.out.println("Please select a team member number:");
                            int playerNum = scanner.nextInt();
                            Player activityPlayer = team.players.stream().filter(item -> item.num == playerNum).findFirst().get();
                            team.players.remove(activityPlayer);
                            team.reserves.add(activityPlayer);
                            System.out.println("Removed team member successfully");
                            showTeamInfo(team);
                            change = false;
                            operation = false;
                        } catch (Exception e) {
                            scanner.next();
                            change = false;
                            operation = false;
                        }
                    }
                }
                break;
                case "4":
                    try {
                        System.out.println("Please select the consumable number:");
                        final int consumableNum = scanner.nextInt();
                        System.out.println("Please select the object number:");
                        final int playerNum = scanner.nextInt();
                        Player basketballPlayer = team.players.stream().filter(item -> item.num == playerNum).findFirst().get();
                        Consumable consumable = team.consumables.stream().filter(item -> item.num == consumableNum).findFirst().get();
                        switch (consumable.type) {
                            case DEFENSE:
                                basketballPlayer.addDefense(consumable.effect);
                                break;
                            case STRENGTH:
                                basketballPlayer.addStrength(consumable.effect);
                                break;
                            case ENDURANCE:
                                basketballPlayer.addEndurance(consumable.effect);
                                break;
                        }
                        team.consumables.remove(consumable);
                        System.out.println("Operation is successful!");
                        showTeamInfo(team);
                        operation = false;
                    } catch (Exception e) {
                        operation = false;
                    }
                    break;
                case "exit":
                    operation = false;
                    break;
                default:
                    break;
            }
        }
    }

    public static void showTeamInfo(Team team) {
        System.out.println("Club name: " + team.name + ", Currently available funds: " + team.getAmountStr() + ", Current season points: " + team.integral);
        System.out.println("athlete: ");
        for (Player player : team.players) {
            System.out.println(String.format("%-4s %-13s strength:%-11s defense:%-11s endurance:%-11s role:%-23s",
                    player.num, player.name, player.getStrengthStr(), player.getDefenseStr(), player.getEnduranceStr(), player.role.value));
        }
        System.out.println("substitute: ");
        for (Player reserve : team.reserves) {
            System.out.println(String.format("%-4s %-13s strength:%-11s defense:%-11s endurance:%-11s role:%-23s",
                    reserve.num, reserve.name, reserve.getStrengthStr(), reserve.getDefenseStr(), reserve.getEnduranceStr(), reserve.role.value));
        }
        System.out.println("consumable: ");
        for (Consumable consumable : team.consumables) {
            System.out.println(String.format("%-4s %-30s effect:%-16s %s",
                    consumable.num, consumable.name, consumable.getEffectStr(), consumable.description));
        }
    }
}