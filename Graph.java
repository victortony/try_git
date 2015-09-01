package com.bjut.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Graph implements Serializable {

	private static Graph graph = null;
	private List<Element> elems = new ArrayList<Element>();

	private Stack<Element> backStack = new Stack<Element>();

	private boolean isSetCanvas = false;

	private int canvas_width = 0;

	public boolean isSetCanvas() {
		return isSetCanvas;
	}

	public void setSetCanvas(boolean isSetCanvas) {
		this.isSetCanvas = isSetCanvas;
	}

	public int getCanvas_width() {
		return canvas_width;
	}

	public void setCanvas_width(int canvas_width) {
		this.canvas_width = canvas_width;
	}

	public int getCanvas_height() {
		return canvas_height;
	}

	public void setCanvas_height(int canvas_height) {
		this.canvas_height = canvas_height;
	}

	private int canvas_height = 0;

	public static Graph getInstance() {

		if (graph == null) {
			graph = new Graph();
		}
		return graph;
	}

	private Graph() {
	}

	public synchronized void add(Element e) {
		this.elems.add(e);
	}

	public synchronized void remove(Element e) {
		this.elems.remove(e);
	}

	public synchronized void remove(int idx) {
		this.elems.remove(idx);
	}

	public synchronized int getSize() {
		return this.elems.size();
	}

	public synchronized Element getElem(int idx) {
		return this.elems.get(idx);
	}

	public synchronized void draw(Canvas canvas, Paint paint) {
		// 保存当前paint，把paint赋值为brush，画完后，恢复
		for (Element e : elems) {
			e.draw(canvas, paint);
		}

		// canvas.translate(50, 50);
	}

	public synchronized void clear() {
		this.elems.clear();
	}

	public synchronized int selectedWho(Point p) {

		for (int i = this.elems.size() - 1; i >= 0; i--)
			if (this.elems.get(i).isSelected(p)) {
				return i;
			}
		return -1;
	}

	public synchronized void move(int index, float lengthX, float lengthY) {

		this.elems.get(index).move(lengthX, lengthY);

	}

	public synchronized void rotate(int index, double angle) {
		// Log.i("1", "rotate:"+this.elems.size()+" index:"+index);
		this.elems.get(index).rotate(angle);
	}

	public synchronized void zoom(int index, double rate) {
		// Log.i("1", "zoom:"+this.elems.size()+" index:"+index);
		this.elems.get(index).zoom(rate);
	}

	// ����
	public synchronized void undo() {
		if (!this.elems.isEmpty()) {
			Element lastElm = this.elems.get(this.elems.size() - 1);
			this.elems.remove(this.elems.size() - 1);
			this.backStack.push(lastElm);
		}
	}

	// ������
	public synchronized void redo() {
		if (!this.backStack.isEmpty()) {
			this.elems.add(this.backStack.pop());
		}
	}

	public synchronized void copy(int index) throws CloneNotSupportedException {
		if (index < this.elems.size()) {
			Element elemCopy = (Element) graph.getElem(index).clone();
			this.elems.add(elemCopy);
		}
	}

	public static void delGraph() {
		graph = null;
	}
}
