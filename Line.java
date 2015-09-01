package com.bjut.model;

import com.baidu.frontia.demo.R;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;

public class Line extends Element {

	public Line(int color, int land) {
		super();
		setColor(color);
		setLand(land);
		// changed = false;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		Line line = new Line(getColor(), getLand());
		line.setPointStart((PointF) pointStart.clone());
		line.setPointEnd((PointF) pointEnd.clone());
		return line;

	}

	@SuppressLint("ResourceAsColor")
	@Override
	public void draw(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub

		paint.setColor(getColor());
		paint.setStrokeWidth(getLand());
		if (isDash()) {
			paint.setStrokeWidth(7);
			PathEffect effects = new DashPathEffect(new float[] { 1, 2, 4, 8 },
					1);
			paint.setPathEffect(effects);
			paint.setColor(R.color.gray);
		}
		canvas.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y,
				paint);
		if (isDash()) {
			paint.setPathEffect(null);
			paint.setStrokeWidth(getLand());
			paint.setColor(getColor());
		}
	}

	@Override
	void rotate(double angle) {

		float centerX = (getPointStart().x + getPointEnd().x) / 2;
		float centerY = (getPointStart().y + getPointEnd().y) / 2;

		float p2x = 0f, p2y = 0f;
		PointF p1 = null;

		// for PointStart
		p1 = getPointStart();
		p2x = (float) (centerX + (p1.x - centerX) * Math.cos(angle) - (p1.y - centerY)
				* Math.sin(angle));
		p2y = (float) (centerY + (p1.y - centerY) * Math.cos(angle) + (p1.x - centerX)
				* Math.sin(angle));
		setPointStart(new PointF(p2x, p2y));

		// for PointEnd
		p1 = getPointEnd();
		p2x = (float) (centerX + (p1.x - centerX) * Math.cos(angle) - (p1.y - centerY)
				* Math.sin(angle));
		p2y = (float) (centerY + (p1.y - centerY) * Math.cos(angle) + (p1.x - centerX)
				* Math.sin(angle));
		setPointEnd(new PointF(p2x, p2y));

		return;
	}

	@Override
	void zoom(double rate) {

		float centerX = (getPointStart().x + getPointEnd().x) / 2;
		float centerY = (getPointStart().y + getPointEnd().y) / 2;

		float p2x = 0f, p2y = 0f;
		PointF p1 = null;

		// for PointStart
		p1 = getPointStart();
		p2x = (float) (centerX + (p1.x - centerX) * rate);
		p2y = (float) (centerY + (p1.y - centerY) * rate);
		setPointStart(new PointF(p2x, p2y));

		// for PointEnd
		p1 = getPointEnd();
		p2x = (float) (centerX + (p1.x - centerX) * rate);
		p2y = (float) (centerY + (p1.y - centerY) * rate);
		setPointEnd(new PointF(p2x, p2y));

	}

	@Override
	public void move(float lengthX, float lengthY) {
		// TODO 自动生成的方法存根
		// getPointStart().x = getPointStart().x+lengthX;
		// getPointStart().y = getPointStart().y+lengthY;

		this.pointStart.x = this.pointStart.x + lengthX;
		this.pointStart.y = this.pointStart.y + lengthY;

		this.pointEnd.x = this.pointEnd.x + lengthX;
		this.pointEnd.y = this.pointEnd.y + lengthY;
	}

	public boolean isSelected(Point p) {
		// TODO Auto-generated method stub

		PointF pStart = new PointF(pointStart.x, pointStart.y);
		PointF pEnd = new PointF(pointEnd.x, pointEnd.y);

		float maxX, maxY, minX, minY;

		if (pStart.x > pEnd.x) {
			maxX = pStart.x;
			minX = pEnd.x;
		} else {
			maxX = pEnd.x;
			minX = pStart.x;
		}

		if (pStart.y > pEnd.y) {
			maxY = pStart.y;
			minY = pEnd.y;
		} else {
			maxY = pEnd.y;
			minY = pStart.y;
		}

		if (!(p.x > minX - 10 && p.x < maxX + 10 && p.y > minY - 10 && p.y < maxY + 10)) {

			return false;
		} else {

			double k = (double) (pEnd.y - pStart.y) / (pEnd.x - pStart.x);
			double b1 = (double) (pStart.y - k * pStart.x);
			double result = p.x * k + b1;

			if (result - 25 <= p.y && p.y <= result + 25) {
				return true;
			} else {
				return false;
			}
		}
	}

	// }

	@Override
	void unSelected() {
		// TODO Auto-generated method stub

	}

}
