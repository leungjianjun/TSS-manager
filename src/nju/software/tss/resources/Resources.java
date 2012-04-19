package nju.software.tss.resources;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.program.Program;

public class Resources {
	
	private static ImageRegistry imageRegistry = JFaceResources.getImageRegistry();
	
	private Resources(){}
	
	Resources resources;
	
	public Resources instance(){
		if(resources==null){
			resources = new Resources();
		}
		return resources;
	}
	
	public static Image getImage(String resourceName){
		Image image = imageRegistry.get(resourceName);
		if(image!=null){
			return image;
		}else{
			image = ImageDescriptor.createFromFile(Resources.class, resourceName).createImage();
			imageRegistry.put(resourceName, image);
			return image;
		}
	}
	
	public static Image getLogoImage(String programName){
		if(programName==null) return getImage(FILE_LOGO);
		Image image = imageRegistry.get(programName);
		if(image!=null){
			return image;
		}else{
			Program prog = Program.findProgram(programName);
			if(prog==null){
				//找不到对应的程序logo
				return getImage(FILE_LOGO);
			}else{
				image = new Image(null,prog.getImageData());
				imageRegistry.put(programName, image);
				return image;
			}
		}
	}
	
	public static ImageDescriptor getImageDescriptor(String resourceName){
		ImageDescriptor imageDescriptor = imageRegistry.getDescriptor(resourceName);
		if(imageDescriptor!=null){
			return imageDescriptor;
		}else{
			imageDescriptor = ImageDescriptor.createFromFile(Resources.class, resourceName);
			imageRegistry.put(resourceName, imageDescriptor);
			return imageDescriptor;
		}
	}
	
	// ////////////////////////////////////////////////////////////////////////////////
	// 一些图片名称的常量
	public static final String TEST_ICON = "login_logo.png";

	public static final String SAMPLES_ICON = "samples.gif";

	public static final String TOC_CLOSED = "toc_closed.gif";
	public static final String TOC_OPEN = "toc_open.gif";
	public static final String TOPIC = "topic.gif";
	public static final String LOGIN_TIP = "login_dialog_tip.png";
	public static final String LOGO = "logo_48.png";
	public static final String USER = "user.png";
	public static final String SYSTEM = "system.png";

	public static final String VERSION = "version.png";
	public static final String TIME = "time.png";
	public static final String SUCCESS = "success.png";
	public static final String SETUP = "setup.png";
	public static final String SETUP_WELCOME = "setup_welcome.png";
	
	public static final String FOLDER_ICON = "folder.png";
	public static final String SEMESTER_ICON = "semester.png";
	public static final String FILE_LOGO = "file.png";

}
