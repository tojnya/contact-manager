package ua.com.javarush.gnew.m2.cli.commands;

import ua.com.javarush.gnew.m2.cli.CliCommand;
import ua.com.javarush.gnew.m2.service.PhoneBookInterface;
import ua.com.javarush.gnew.m2.utils.Utils;
import static picocli.CommandLine.Command;
import static picocli.CommandLine.Parameters;

@Command(name = "search", aliases = {"-s", "--search"},
        mixinStandardHelpOptions = true,
        description = "Шукає контакт за ім'ям")
public  class SearchContact implements CliCommand {

    private final PhoneBookInterface phoneBookInterface;

    @Parameters(index = "0", description = "Фраза для пошуку", arity = "0..1")
    private String name;

    public SearchContact(PhoneBookInterface phoneBookInterface) {
        this.phoneBookInterface = phoneBookInterface;
    }

    @Override
    public Integer call() {
        Utils.printContactList(phoneBookInterface.search(name));
        return 0;
    }
}
