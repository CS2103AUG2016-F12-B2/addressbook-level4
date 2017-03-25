package guitests;

import org.testfx.api.FxRobot;

import f12b2.todoapp.testutil.TestUtil;
import javafx.scene.input.KeyCodeCombination;

/**
 * Robot used to simulate user actions on the GUI.
 * Extends {@link FxRobot} by adding some customized functionality and workarounds.
 */
public class GuiRobot extends FxRobot {

    public GuiRobot push(KeyCodeCombination keyCodeCombination) {
        return (GuiRobot) super.push(TestUtil.scrub(keyCodeCombination));
    }

}
