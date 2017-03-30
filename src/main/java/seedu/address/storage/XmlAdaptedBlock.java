// @@author A0124591H

package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

//@@author A0124591H

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Block;

/**
 * JAXB-friendly adapted version of the Block.
 */
public class XmlAdaptedBlock {

    @XmlValue
    public String blockName;

    /**
     * Constructs an XmlAdaptedBlock.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedBlock() {}

    /**
     * Converts a given Block into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedBlock(Block source) {
        blockName = source.blockPeriodWhole;
    }

    /**
     * Converts this jaxb-friendly adapted block object into the model's Block object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Block toModelType() throws IllegalValueException {
        return new Block(blockName);
    }

}
