import com.dd.playgame.*;


import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        //球队名称
        System.out.println("input team name");
        String teamName = scanner.nextLine();

        //持续周期
        System.out.println("input cycle");
        int cycle = scanner.nextInt();
        int allCycle = cycle;

        Team myTeam = new Team();
        myTeam.name = teamName;
        myTeam.initAmount = BasketBallConfig.INIT_AMOUNT;
        myTeam.amount = myTeam.initAmount;

        System.out.println("created team "+teamName+" success! "+"init amount : "+myTeam.initAmount);

        System.out.println("market data refreshing....");
        Market market = new Market();
        System.out.println("market data refreshed!");

        boolean exit =false;
        while (!exit && cycle>0){
            System.out.println("赛季持续"+allCycle+"周, 当前第"+(allCycle-cycle+1)+"周.");
            System.out.println("请选择你需要的操作:(1/查看俱乐部,2/去比赛,3/查看市场,4/进入下一周,exit/退出游戏)");
            String chose = scanner.next();
            switch (chose) {
                case  "1":
                    teamOperation(scanner,myTeam);
                    break;
                case "2":
                    boolean success = Battle.battleTeam(myTeam);
                    if (success) {
                        showTeamInfo(myTeam);
                    }
                    break;
                case "3":
                    marketOperation(scanner, myTeam, market);
                    break;
                case "4":
                    cycle--;
                    market.refresh();
                    myTeam.refreshPlayerState();
                    break;
                case "exit":
                    exit =true;
                    break;
                default:
                    break;
            }
        }

        if (cycle==0) {
            System.out.println("赛季结束!");
            //正常结束,展示赛季成绩
            showTeamInfo(myTeam);
        }
    }

    private static void marketOperation(Scanner scanner, Team myTeam, Market market) {

        boolean buy = true;
        while (buy){
            market.show();

            System.out.println("请输入购买商品编号:(exit/退出市场)");
            boolean fullPrice = true;
            while (fullPrice){

                String input = scanner.next();
                int num = 0;
                if ("exit".equalsIgnoreCase(input)) {
                    num = -1;
                }else {
                    try {
                        num = Integer.parseInt(input);
                    } catch (NumberFormatException ignored) {
                        num = -1;
                    }
                }

                if (num == -1) {
                    fullPrice=false;
                    buy = false;
                    continue;
                }

                final int inputNum = num;
                Optional<MarketPlayer> findPlayer = market.players.stream().filter(item -> item.num == inputNum).findFirst();
                if (findPlayer.isPresent()) {
                    MarketPlayer player = findPlayer.get();
                    if(myTeam.amount >= player.price){
                        System.out.println("请选择加入队伍或者替补: (1/加入队伍,2/替补)");
                        market.players.remove(player);
                        String choseStr = scanner.next();
                        int chose = 0;
                        try {
                            chose = Integer.parseInt(choseStr);
                        } catch (NumberFormatException ignored) {
                        }
                        if (chose ==1) {
                            Optional<BasketballPlayer> first = myTeam.players.stream().filter(item -> item.role == player.role).findFirst();
                            if (first.isPresent()) {
                                BasketballPlayer it = first.get();
                                myTeam.players.remove(it);
                                myTeam.reserves.add(it);
                                myTeam.players.add(new BasketballPlayer(player));
                            }else {
                                myTeam.players.add(new BasketballPlayer(player));
                            }
                        }else {
                            myTeam.reserves.add(new BasketballPlayer(player));
                        }
                        myTeam.amount = myTeam.amount - player.price;
                        System.out.println("购买成功,当前余额("+ myTeam.getAmount()+"),是否继续(y/n)?");
                        buy = "y".equalsIgnoreCase(scanner.next());
                        fullPrice= false;
                    }else {
                        System.out.println("资金不足!");
                    }
                }else {
                    Optional<MarketConsumable> findConsumable = market.consumables.stream().filter(item -> item.num == inputNum).findFirst();
                    if (findConsumable.isPresent()) {
                        MarketConsumable consumable =findConsumable.get();
                        if(myTeam.amount >= consumable.price){
                            market.consumables.remove(consumable);
                            myTeam.consumables.add(consumable);
                            myTeam.amount = myTeam.amount - consumable.price;
                            System.out.println("购买成功,当前余额("+ myTeam.getAmount()+"),是否继续(y/n)?");
                            buy = "y".equalsIgnoreCase(scanner.next());
                            fullPrice= false;
                        }else {
                            System.out.println("资金不足!");
                        }
                    }else {
                        System.out.println("未找到编号为"+inputNum+"的商品!");
                    }
                }
            }
        }
    }

    private static void teamOperation(Scanner scanner, Team team) {
        showTeamInfo(team);
        boolean operation = true;
        while (operation) {
            System.out.println("请选择你需要的操作:(1/交换队员,2/添加队员,3/移除队员,4/使用消耗品,exit/退出操作");
            String index = scanner.next();
            switch (index) {
                case "1": {
                    boolean change = true;
                    while (change){
                        try {
                            System.out.println("请选择队伍内成员编号:");
                            final int playerNum = scanner.nextInt();
                            System.out.println("请选择替补队伍内成员编号:");
                            final int reserveNum =scanner.nextInt();
                            BasketballPlayer activityPlayer = team.players.stream().filter(item -> item.num == playerNum).findFirst().get();
                            BasketballPlayer reservePlayer = team.reserves.stream().filter(item -> item.num == reserveNum).findFirst().get();
                            if (activityPlayer.role != reservePlayer.role) {
                                System.out.println("需要交换的运动员角色不匹配,请重新选择!");
                            }else if(reservePlayer.endurance <= 0d ){
                                System.out.println("替补队员已经没有力气再上场了,请重新选择!");
                            }else {
                                team.players.remove(activityPlayer);
                                team.players.add(reservePlayer);

                                team.reserves.remove(reservePlayer);
                                team.reserves.add(activityPlayer);
                                System.out.println("交换成功");
                                showTeamInfo(team);
                                change = false;
                                operation = false;
                            }
                        } catch (Exception e) {
                            scanner.next();
                            change =false;
                            operation=false;
                        }
                    }
                }
                break;
                case "2": {
                    boolean change = true;
                    while (change) {
                        try {
                            System.out.println("请选择替补队伍内成员编号:");
                            final int reserveNum = scanner.nextInt();
                            BasketballPlayer reservePlayer = team.reserves.stream().filter(item -> item.num == reserveNum).findFirst().get();

                            if (reservePlayer.endurance <= 0d) {
                                System.out.println("替补队员已经没有力气再上场了,请重新选择!");
                            }else {
                                Optional<BasketballPlayer> activityPlayer = team.players.stream().filter(item -> item.role == reservePlayer.role)
                                        .findFirst();
                                activityPlayer.ifPresent(itemPlayer ->{
                                    team.players.remove(itemPlayer);
                                    team.reserves.add(itemPlayer);
                                });
                                team.players.add(reservePlayer);
                                team.reserves.remove(reservePlayer);
                                System.out.println("添加队员成功");
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
                            System.out.println("请选择队伍内成员编号:");
                            int playerNum = scanner.nextInt();
                            BasketballPlayer activityPlayer = team.players.stream().filter(item -> item.num == playerNum).findFirst().get();
                            team.players.remove(activityPlayer);
                            team.reserves.add(activityPlayer);
                            System.out.println("移除队员成功");
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
                        System.out.println("请选择使用消耗品编号:");
                        final int consumableNum = scanner.nextInt();
                        System.out.println("请选择使用对象编号:");
                        final int playerNum = scanner.nextInt();
                        BasketballPlayer basketballPlayer = team.players.stream().filter(item -> item.num == playerNum).findFirst().get();
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
                        System.out.println("操作成功!");
                        showTeamInfo(team);
                        operation = false;
                    } catch (Exception e) {
                        operation =false;
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

    private static void showTeamInfo(Team team) {
        System.out.println("俱乐部名称: "+ team.name+", 当前可用资金: "+ team.getAmount() +", 当前赛季积分: "+ team.getIntegral());
        System.out.println("运动员: ");
        PrintUtils.showPlayers(team.players);
        System.out.println("替补队员: ");
        PrintUtils.showPlayers(team.reserves);
        System.out.println("消耗品: ");
        PrintUtils.showConsumables(team.consumables);
    }
}