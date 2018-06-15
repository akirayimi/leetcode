package cn.akirayimi.robot;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.concurrent.TimeUnit;

public class Mouse  {
	public static void main(String[] args) throws AWTException, InterruptedException {
		Robot r = new Robot();
		while(true){
			r.mouseWheel(-1);
			TimeUnit.SECONDS.sleep(1);
		}
	}
}