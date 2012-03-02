package nju.software.tss.resources;

import java.util.Enumeration;
import java.util.Hashtable;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

/**
 * @deprecated
 * @author ljj09
 *
 */
public class ImageFactory {

	// 将构造方法设置为private，禁止创建该类的实例
	private ImageFactory() {}

	// 图片保存的绝对地址
	
	// 一些图片名称的常量
	public static final String TEST_ICON = "test.png";

	public static final String SAMPLES_ICON = "samples.gif";
	
	public static final String TOC_CLOSED = "toc_closed.gif";
	public static final String TOC_OPEN = "toc_open.gif";
	public static final String TOPIC = "topic.gif";

	// 使用Hashtable保存使用的图片资源
	private static Hashtable<String,Image> htImage = new Hashtable<String,Image>();

	// 加载图片.首先从htImage中获得图片对象，
	// 如果没有，则加载新的图片并放入到htImage
	public static Image loadImage( String imageName) {
		Image image = (Image) htImage.get(imageName.toUpperCase());
		if (image == null) {
			image = ImageDescriptor.createFromURL(ImageFactory.class.getResource(imageName)).createImage();
			htImage.put(imageName.toUpperCase(), image);
		}
		return image;
	}

	// 释放htImage中的所有的图片资源
	public static void dispose() {
		Enumeration<Image> e = htImage.elements();
		while (e.hasMoreElements()) {
			Image image = (Image) e.nextElement();
			if (!image.isDisposed())
				image.dispose();
		}
	}

}
