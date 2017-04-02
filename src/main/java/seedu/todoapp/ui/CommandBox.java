package seedu.todoapp.ui;

import java.text.ParseException;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.todoapp.commons.core.LogsCenter;
import seedu.todoapp.commons.events.ui.NewResultAvailableEvent;
import seedu.todoapp.commons.exceptions.IllegalValueException;
import seedu.todoapp.commons.util.FxViewUtil;
import seedu.todoapp.logic.Logic;
import seedu.todoapp.logic.commands.CommandResult;
import seedu.todoapp.logic.commands.exceptions.CommandException;
import seedu.todoapp.model.person.UniqueTaskList.TaskInvalidTimestampsException;

public class CommandBox extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";
    public static final String ERROR_STYLE_CLASS = "error";

    private final Logic logic;

    @FXML
    private TextField commandTextField;

    public CommandBox(AnchorPane commandBoxPlaceholder, Logic logic) {
        super(FXML);
        this.logic = logic;
        addToPlaceholder(commandBoxPlaceholder);
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(commandTextField);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 0.0, 0.0);
    }

    @FXML
    private void handleCommandInputChanged()
            throws IllegalValueException, ParseException, TaskInvalidTimestampsException {
        try {
            CommandResult commandResult = logic.execute(commandTextField.getText());

            // process result of the command
            setStyleToIndicateCommandSuccess();
            commandTextField.setText("");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException e) {
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }


    /**
     * Sets the command box style to indicate a successful command.
     */
    private void setStyleToIndicateCommandSuccess() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        commandTextField.getStyleClass().add(ERROR_STYLE_CLASS);
    }

}
