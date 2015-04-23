/**
 * 
 */
package uibk.sup.ivanka.util;

/**
 * @author Daniel
 *
 */
public class List<Element> {

	private Node first;
	private Node last;
	private Node penultimate;
	private int size;

	public List() {

		this.first = null;
		this.penultimate = this.first;
		this.last = this.first;
		this.size = 0;

	}

	/**
	 * 
	 * This method adds a Element at the end of the List
	 * 
	 * 
	 * @param newElement
	 */
	public void addElement(Element newElement) {

		if (size == 0) {
			first = new Node();
			first.element = newElement;
			first.next = null;
			last = first;
			penultimate = first;
		} else if (size == 1) {
			last = new Node();
			last.element = newElement;
			last.next = null;
			first.next = last;
			penultimate = first;
		} else {
			penultimate = last;
			last = new Node();
			penultimate.next = last;
			last.element = newElement;
			last.next = null;
		}
		size++;
	}

	/**
	 * This method gets an Array of Elements and ads them in the getted order in
	 * front of the old list
	 * 
	 * 
	 * @param newElements
	 */
	public void addElementsAtBegin(Element[] newElements) {

		Node newElement;
		for (int i = newElements.length; i > 0; i--) {
			if (size == 0) {
				first = new Node();
				first.element = newElements[i];
				first.next = null;
				last = first;
				penultimate = first;
			} else if (size == 1) {
				newElement = new Node();
				newElement.element = newElements[i];
				newElement.next = first;
				first = newElement;
				penultimate = newElement;
			} else {

				newElement = new Node();
				newElement.element = newElements[i];
				newElement.next = first;
				first = newElement;

			}
			size += newElements.length;
		}
	}

	/**
	 * This method returns the Element at the searched position
	 * 
	 * 
	 * @param position
	 * @return
	 */
	public Element get(int position) {

		if (position <= size - 1 && position >= 0) {
			Node ret = first;
			int counter = 0;
			while (counter != position) {
				ret = ret.next;
				counter++;
			}
			return ret.element;
		}
		return null;
	}

	/**
	 * This method returns the Element at the first position and delets it
	 * 
	 * 
	 * @param position
	 * @return
	 */
	public Element getElement() {

		if (first == null)
			return null;
		Element ret = first.element;
		first.element = null;
		first = first.next;
		removeElement(1);
		return ret;
	}

	/**
	 * This method removes the element at the given position
	 * 
	 * 
	 * @param position
	 */
	public void removeElement(int position) {

		if (size == 0 || position > size)
			return;
		if (size == 1) {
			first.element = null;
			first.next = null;
			penultimate = first;
			last = first;
		} else {
			Node help = first;
			Node savePrevious = new Node();
			savePrevious.next = first;
			int count = 0;
			while (help.next != null) {
				count++;
				if (count == position) {
					help.element = null;
					savePrevious.next = help.next;
					help.next = null;
					if (size == 2)
						penultimate = first;
				}
				help = help.next;
				savePrevious = savePrevious.next;
			}
		}
		size--;
	}

	/**
	 * This method destroys the hole list
	 * 
	 * 
	 */
	public void destroyList() {

		Node help = first;
		while (help.next != null) {
			help = help.next;
			first.element = null;
			first.next = null;
			first = help;
		}
		help.element = null;
		first = null;
		penultimate = first;
		last = first;
		size = 0;
	}

	public int size() {
		return size;
	}

	private class Node {

		Element element;
		Node next;
	}

}
