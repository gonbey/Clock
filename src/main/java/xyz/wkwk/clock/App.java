package xyz.wkwk.clock;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.MouseInputAdapter;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		new App().exec();
	}

	public void exec() {
		final JFrame fr = new JFrame();
		fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		fr.setSize(1000, 200);
		// 装飾外し
		fr.setUndecorated(true);
		// 透過
		fr.setBackground(new Color(0, 0, 0, 0));
		// 最前面
		fr.setAlwaysOnTop(true);

		// 最小化されないようにする
		fr.addWindowListener(new WindowAdapter() {
			@Override
			public void windowIconified(WindowEvent e) {
				fr.setState(JFrame.NORMAL);
			}
		});

		// ラベル追加
		final JLabel lblClock = new JLabel("moge");
		Font font = new Font("Arial", Font.BOLD, 120);
		lblClock.setFont(font);
		DragListener dl = new DragListener(fr);
		lblClock.addMouseMotionListener(dl);
		lblClock.setBackground(new Color(0, 0, 0, 128));
		lblClock.addMouseListener(dl);
		fr.add(lblClock);

		// 時間更新
		boolean[] isRunnning = {true};
		Thread timer = new Thread(new Runnable() {
			public void run() {
				while(isRunnning[0]) {
					Calendar now = Calendar.getInstance();
					int hour = now.get(Calendar.HOUR);
					int min   = now.get(Calendar.MINUTE);
					int sec   = now.get(Calendar.SECOND);
					int msec  = now.get(Calendar.MILLISECOND);

					try {
						TimeUnit.MILLISECONDS.sleep(33);
						lblClock.setText(String.format("%d:%d:%d:%d", hour, min, sec, msec));
					} catch (InterruptedException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
				}
			}

		});
		timer.start();

		fr.setVisible(true);
		fr.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent ent) {
				isRunnning[0] = false;
			}
		});
	}
}

class DragListener extends MouseInputAdapter {
	Point location;
	MouseEvent pressed;
	Component target;

	public DragListener(Component target) {
		this.target = target;
	}

	public void mousePressed(MouseEvent me) {
		pressed = me;
	}

	public void mouseDragged(MouseEvent me) {
//		Component component = me.getComponent();
//		location = component.getLocation(location);
		location = this.target.getLocation(location);
		int x = location.x - pressed.getX() + me.getX();
		int y = location.y - pressed.getY() + me.getY();
		target.setLocation(x, y);
	}
}
