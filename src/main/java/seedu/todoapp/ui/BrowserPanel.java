package seedu.todoapp.ui;

import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.todoapp.commons.util.FxViewUtil;
import seedu.todoapp.model.person.ReadOnlyTask;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    private static final String FXML = "BrowserPanel.fxml";

    @FXML
    private WebView browser;

    /**
     * @param placeholder The AnchorPane where the BrowserPanel must be inserted
     */
    public BrowserPanel(AnchorPane placeholder) {
        super(FXML);
        placeholder.setOnKeyPressed(Event::consume); // To prevent triggering events for typing inside the
                                                     // loaded Web page.
        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
        placeholder.getChildren().add(browser);
    }

    public void loadTaskPage(ReadOnlyTask task) {
        loadPage("https://www.google.com/maps/place/" + task.getVenue().value.replaceAll(" ", "%20"));
    }

    public void loadPage(String url) {
        browser.getEngine().load(url);
        this.fontFix();
    }

    //@@author: A0114395E
    /*
     * Helper function to hotfix the fonts (Open Sans) which are failing on MacOS.
     * See Issue : https://github.com/se-edu/addressbook-level4/issues/374
     * Solution: Wait for browser to load before executing JS
     * TODO: This still doesn't work.. unfortunately :( . Works on browsers outside of java though.
     */
    private void fontFix() {
        // on ready function
        browser.getEngine().getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                // new page has loaded, process:
                // Replace fonts when document is ready
                final String javascriptCSSCode = "var style = document.createElement('style');";
                final String javascriptTagCode = "var css = 'body { font-family: \\'arial,sans-serif\\'; }';";
                final String javascriptHeadCode = "head = document.head || document.getElementsByTagName('head')[0];";
                final String javascriptSetTagType = "style.type = 'text/css';";
                final String javascriptAddCSSToStyleTag = "(style.styleSheet) ? "
                        + "style.styleSheet.cssText = css : style.appendChild(document.createTextNode(css));";
                final String javascriptAppendStyleToHead = "head.appendChild(style);";

                // Create javascript function
                final String javascriptFunction = "var changeStyle = function() {"
                        .concat(javascriptCSSCode)
                        .concat(javascriptTagCode)
                        .concat(javascriptHeadCode)
                        .concat(javascriptSetTagType)
                        .concat(javascriptAddCSSToStyleTag)
                        .concat(javascriptAppendStyleToHead)
                        .concat(" }");

                // Run javascript function
                browser.getEngine().executeScript(javascriptFunction);
                browser.getEngine().executeScript("setTimeout(changeStyle, 2000);");
            }
        });
    }
    //@@author

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

}
