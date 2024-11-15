package ua.com.javarush.gnew.m2.cli.commands;

import java.io.IOException;
import picocli.CommandLine;
import ua.com.javarush.gnew.m2.cli.CliCommand;
import ua.com.javarush.gnew.m2.configuration.PhoneBookContext;
import ua.com.javarush.gnew.m2.service.SettingsServiceInterface;

@CommandLine.Command(
    name = "locale",
    aliases = {"-l", "--locale"},
    mixinStandardHelpOptions = true,
    description = "Please, choose yours language")
public class SetLocale implements CliCommand {

  private final SettingsServiceInterface settingsService =
      PhoneBookContext.getBean(SettingsServiceInterface.class);

  @CommandLine.Parameters(index = "0", description = "language", arity = "1")
  private String newLocale;

  @Override
  public Integer call() {
    try {
      settingsService.setLocale(newLocale);
      System.out.println("Локаль установлена на: " + newLocale);
      return 0;
    } catch (IOException e) {
      System.err.println("Ошибка при установке локали: " + e.getMessage());
      return 1;
    }
  }
}
