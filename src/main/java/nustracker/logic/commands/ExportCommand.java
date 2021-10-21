package nustracker.logic.commands;

import static java.util.Objects.requireNonNull;
import static nustracker.logic.parser.CliSyntax.PREFIX_NAME;

import java.nio.file.Path;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import nustracker.logic.commands.exceptions.CommandException;
import nustracker.model.Model;
import nustracker.model.student.Student;
import nustracker.storage.Exporting;

/**
 * Exports the emails of the students in the current displayed list.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_SUCCESS = "Emails exported";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the email of all the current displayed students as a csv file.\n"
            + "Parameters: "
            + PREFIX_NAME + "FILE_NAME\n"
            + "Example: " + COMMAND_WORD
            + " "
            + PREFIX_NAME + "exportedEmails";

    private final Path pathToExport;

    public ExportCommand(String pathToExport) {
        requireNonNull(pathToExport);
        this.pathToExport = Path.of("data\\" + pathToExport + ".csv");
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Student> filteredStudents = model.getFilteredStudentList();

        Exporting.exportEmails(
                pathToExport,
                filteredStudents.stream().map(Student::getEmail).collect(Collectors.toList()));

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
