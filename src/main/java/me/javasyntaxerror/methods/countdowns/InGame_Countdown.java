package me.javasyntaxerror.methods.countdowns;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import me.javasyntaxerror.Cores;
import me.javasyntaxerror.methods.mysql.Stats;
import me.javasyntaxerror.methods.others.GameState;

public class InGame_Countdown {

	public static Integer count = 0;
	private static int redRight = 0;
	private static int redLeft = 0;
	private static int blueRight = 0;
	private static int blueLeft = 0;
	private static boolean stoped = false;

	@SuppressWarnings({ "static-access", "deprecation" })
	public static void start() {
		Thread t = Thread.currentThread();

		Cores.getInstance().setState(GameState.INGAME);

		for (int i = 0; i != -1; i++) {
			if(stoped) {
				return;
			}
			
			Cores.getInstance().executorService.submit(() ->{
				for (Player all : Bukkit.getOnlinePlayers()) {
					if (Cores.getInstance().team.get(all.getName()) != null) {
						if (Cores.getInstance().team.get(all.getName()).equals("Spectator")) {
							Cores.getInstance().actionBar.sendActionBar(all, "§7Spectator");
						} else {
							Cores.getInstance().actionBar.sendActionBar(all, Cores.getInstance().teamManager.get(Cores.getInstance().team.get(all.getName())).getPrefix() + "Team " + Cores.getInstance().team.get(all.getName()));
						}
					}
				}
			});
			Cores.getInstance().executorService.submit(() ->{
				for(Player all : Bukkit.getOnlinePlayers()) {
					String team = Cores.getInstance().team.get(all.getName());
					
					if(team.equalsIgnoreCase("Rot")) {				
						if(redRight < 1) {
							if(Cores.getInstance().locationManager.getLocation(all, "blaurightcore").distance(all.getLocation()) <= 5) {
								redRight = 1;

								if(Cores.getInstance().teamManager.get("Blau").isRightCore()) {
									if(Cores.getInstance().teamManager.get("Blau").getRight() == 0) {
										Cores.getInstance().teamManager.get("Blau").setRight(1);
										
										for(Player p : Bukkit.getOnlinePlayers()) {
											Cores.getInstance().scoreboardManager.updateRightCore(p, 1, 1);
										}
									} else if(Cores.getInstance().teamManager.get("Blau").getRight() == 1) {
										Cores.getInstance().teamManager.get("Blau").setRight(0);
										
										for(Player p : Bukkit.getOnlinePlayers()) {
											Cores.getInstance().scoreboardManager.updateRightCore(p, 1, 0);
										}
									}
									
									for(String string : Cores.getInstance().teamManager.get("Blau").getPlayers()) {
										Player player = Bukkit.getPlayerExact(string);
										player.playSound(player.getLocation(), Sound.NOTE_BASS_GUITAR, 1, 1);
									}
								}
							} else {
								if(Cores.getInstance().teamManager.get("Blau").getRight() == 1) {
									Cores.getInstance().teamManager.get("Blau").setRight(0);
									
									for(Player p : Bukkit.getOnlinePlayers()) {
										Cores.getInstance().scoreboardManager.updateRightCore(p, 1, 0);
									}
								}
							}
						}
						if(redLeft < 1) {
							if(Cores.getInstance().locationManager.getLocation(all, "blauleftcore").distance(all.getLocation()) <= 5) {
								redLeft = 1;

								if(Cores.getInstance().teamManager.get("Blau").isLeftCore()) {
									if(Cores.getInstance().teamManager.get("Blau").getLeft() == 0) {
										Cores.getInstance().teamManager.get("Blau").setLeft(1);
										
										for(Player p : Bukkit.getOnlinePlayers()) {
											Cores.getInstance().scoreboardManager.updateLeftCore(p, 1, 1);
										}
									} else if(Cores.getInstance().teamManager.get("Blau").getLeft() == 1) {
										Cores.getInstance().teamManager.get("Blau").setLeft(0);
										
										for(Player p : Bukkit.getOnlinePlayers()) {
											Cores.getInstance().scoreboardManager.updateLeftCore(p, 1, 0);
										}
									}
									
									for(String string : Cores.getInstance().teamManager.get("Blau").getPlayers()) {
										Player player = Bukkit.getPlayerExact(string);
										player.playSound(player.getLocation(), Sound.NOTE_BASS_GUITAR, 1, 1);
									}
								}
							} else {
								if(Cores.getInstance().teamManager.get("Blau").getLeft() == 1) {
									Cores.getInstance().teamManager.get("Blau").setLeft(0);
									
									for(Player p : Bukkit.getOnlinePlayers()) {
										Cores.getInstance().scoreboardManager.updateLeftCore(p, 1, 0);
									}
								}
							}
						}
					} else if(team.equalsIgnoreCase("Blau")) {
						if(blueRight < 1) {
							if(Cores.getInstance().locationManager.getLocation(all, "rotrightcore").distance(all.getLocation()) <= 5) {
								blueRight = 1;

								if(Cores.getInstance().teamManager.get("Rot").isRightCore()) {
									if(Cores.getInstance().teamManager.get("Rot").getRight() == 0) {
										Cores.getInstance().teamManager.get("Rot").setRight(1);
										
										for(Player p : Bukkit.getOnlinePlayers()) {
											Cores.getInstance().scoreboardManager.updateRightCore(p, 0, 1);
										}
									} else if(Cores.getInstance().teamManager.get("Rot").getRight() == 1) {
										Cores.getInstance().teamManager.get("Rot").setRight(0);
										
										for(Player p : Bukkit.getOnlinePlayers()) {
											Cores.getInstance().scoreboardManager.updateRightCore(p, 0, 0);
										}
									}
									
									for(String string : Cores.getInstance().teamManager.get("Rot").getPlayers()) {
										Player player = Bukkit.getPlayerExact(string);
										player.playSound(player.getLocation(), Sound.NOTE_BASS_GUITAR, 1, 1);
									}
								}
							} else {
								if(Cores.getInstance().teamManager.get("Rot").getRight() == 1) {
									Cores.getInstance().teamManager.get("Rot").setRight(0);
									
									for(Player p : Bukkit.getOnlinePlayers()) {
										Cores.getInstance().scoreboardManager.updateRightCore(p, 0, 0);
									}
								}
							}
						}
						if(blueLeft < 1) {
							if(Cores.getInstance().locationManager.getLocation(all, "rotleftcore").distance(all.getLocation()) <= 5) {
								blueLeft = 1;

								if(Cores.getInstance().teamManager.get("Rot").isLeftCore()) {
									if(Cores.getInstance().teamManager.get("Rot").getLeft() == 0) {
										Cores.getInstance().teamManager.get("Rot").setLeft(1);
										
										for(Player p : Bukkit.getOnlinePlayers()) {
											Cores.getInstance().scoreboardManager.updateLeftCore(p, 0, 1);
										}
									} else if(Cores.getInstance().teamManager.get("Rot").getLeft() == 1) {
										Cores.getInstance().teamManager.get("Rot").setLeft(0);
										
										for(Player p : Bukkit.getOnlinePlayers()) {
											Cores.getInstance().scoreboardManager.updateLeftCore(p, 0, 0);
										}
									}
									
									for(String string : Cores.getInstance().teamManager.get("Rot").getPlayers()) {
										Player player = Bukkit.getPlayerExact(string);
										player.playSound(player.getLocation(), Sound.NOTE_BASS_GUITAR, 1, 1);
									}
								}
							} else {
								if(Cores.getInstance().teamManager.get("Rot").getLeft() == 1) {
									Cores.getInstance().teamManager.get("Rot").setLeft(0);
									
									for(Player p : Bukkit.getOnlinePlayers()) {
										Cores.getInstance().scoreboardManager.updateLeftCore(p, 0, 0);
									}
								}
							}
						}
					}
				}
				
				redRight = 0;
				redLeft = 0;
				
				blueRight = 0;
				blueLeft = 0;
			});
			Cores.getInstance().executorService.submit(() ->{
				if (Cores.getInstance().teams.size() <= 1) {
					if (Cores.getInstance().teams.size() != 0) {
						if (Cores.getInstance().teams.get(0) != null) {
							String team = Cores.getInstance().teams.get(0);

							if (Cores.getInstance().mysqlactivated) {
								for (String strings : Cores.getInstance().teamManager.get(team).getPlayers()) {
									Player target = Bukkit.getPlayer(strings);

									Stats.addGewonneSpiele(target.getUniqueId().toString(), 1);
								}
							}

							for(Player all : Bukkit.getOnlinePlayers()) {
								String subtitle = Cores.translation("vicotry.subtitle","%PREFIX% Team %TEAM% §7hat §agewonnen§8!" );
								subtitle = Cores.replace(subtitle, "%PREFIX%", Cores.getInstance().teamManager.get(team).getPrefix());
								subtitle = Cores.replace(subtitle, "%TEAM%", team);
								all.sendTitle(Cores.translation("victory.title", "§b§lCores"),  subtitle);
							}
							
							Bukkit.getScheduler().callSyncMethod(Cores.getInstance(), () -> {
								if (Bukkit.getOnlinePlayers().size() != 0) {
									for (Player all : Bukkit.getOnlinePlayers()) {
										for (Player player : Bukkit.getOnlinePlayers()) {
											all.showPlayer(player);
										}

										Cores.getInstance().scoreboardManager.setScoreboardTeamLobby(all);
									}
								}
								return null;
							});
						}
					}
					
					stoped = true;

					Restart_Countdown.start();
					return;
				} else {
					if (Cores.getInstance().teams != null) {
						Bukkit.getScheduler().callSyncMethod(Cores.getInstance(), () -> {
							for (String team : Cores.getInstance().teams) {
								if (team != null) {
									if (Cores.getInstance().teamManager.get(team).getPlayersSize() == 0) {
										Cores.getInstance().teams.remove(team);
									}
								}
							}
							for (Player all : Bukkit.getOnlinePlayers()) {
								Cores.getInstance().scoreboardManager.updateTime(all);
							}
							return null;
						});
					}
				}
			});
			

			count += 1;
			try {
				t.sleep(1 * 999);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
