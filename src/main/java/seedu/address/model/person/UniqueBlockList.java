// @@author A0124591H

package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of blocks that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Block#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueBlockList implements Iterable<Block> {

    private final ObservableList<Block> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty BlockList.
     */
    public UniqueBlockList() {}

    /**
     * Creates a UniqueBlockList using given String blocks.
     * Enforces no nulls or duplicates.
     */
    public UniqueBlockList(String... blocks) throws DuplicateBlockException, IllegalValueException {
        final List<Block> blockList = new ArrayList<Block>();
        for (String block : blocks) {
            blockList.add(new Block(block));
        }
        setBlocks(blockList);
    }

    /**
     * Creates a UniqueBlockList using given blocks.
     * Enforces no nulls or duplicates.
     */
    public UniqueBlockList(Block... blocks) throws DuplicateBlockException {
        assert !CollectionUtil.isAnyNull((Object[]) blocks);
        final List<Block> initialBlocks = Arrays.asList(blocks);
        if (!CollectionUtil.elementsAreUnique(initialBlocks)) {
            throw new DuplicateBlockException();
        }
        internalList.addAll(initialBlocks);
    }

    /**
     * Creates a UniqueBlockList using given blocks.
     * Enforces no null or duplicate elements.
     */
    public UniqueBlockList(Collection<Block> blocks) throws DuplicateBlockException {
        this();
        setBlocks(blocks);
    }

    /**
     * Creates a UniqueBlockList using given blocks.
     * Enforces no nulls.
     */
    public UniqueBlockList(Set<Block> blocks) {
        assert !CollectionUtil.isAnyNull(blocks);
        internalList.addAll(blocks);
    }

    /**
     * Creates a copy of the given list.
     * Insulates from changes in source.
     */
    public UniqueBlockList(UniqueBlockList source) {
        internalList.addAll(source.internalList); // insulate internal list from changes in argument
    }

    /**
     * Returns all blocks in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Block> toSet() {
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Blocks in this list with those in the argument block list.
     */
    public void setBlocks(UniqueBlockList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setBlocks(Collection<Block> blocks) throws DuplicateBlockException {
        assert !CollectionUtil.isAnyNull(blocks);
        if (!CollectionUtil.elementsAreUnique(blocks)) {
            throw new DuplicateBlockException();
        }
        internalList.setAll(blocks);
    }

    /**
     * Ensures every block in the argument list exists in this object.
     */
    public void mergeFrom(UniqueBlockList from) {
        final Set<Block> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(block -> !alreadyInside.contains(block))
                .forEach(internalList::add);
    }

    /**
     * Returns true if the list contains an equivalent Block as the given argument.
     */
    public boolean contains(Block toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Block to the list.
     *
     * @throws DuplicateBlockException if the Block to add is a duplicate of an existing Block in the list.
     */
    public void add(Block toAdd) throws DuplicateBlockException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateBlockException();
        }
        internalList.add(toAdd);
    }

    @Override
    public Iterator<Block> iterator() {
        return internalList.iterator();
    }

    public UnmodifiableObservableList<Block> asObservableList() {
        return new UnmodifiableObservableList<>(internalList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueBlockList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueBlockList) other).internalList));
    }

    public boolean equalsOrderInsensitive(UniqueBlockList other) {
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateBlockException extends DuplicateDataException {
        protected DuplicateBlockException() {
            super("Operation would result in duplicate blocks");
        }
    }

}
