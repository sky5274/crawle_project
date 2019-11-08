package com.sky.test;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.HOGDescriptor;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

/**
 * 	机器人脸识别
 * @author 王帆
 * @date  2019年11月2日 下午12:30:15
 */
public class CaptureCheckFaceBasic extends JPanel {  

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage mImg;  
	private static String openvcsrc="D:\\Program Files\\opencv\\opencv";
	
	private BufferedImage matToImg(Mat mat){  
		int dataSize =mat.cols()*mat.rows()*(int)mat.elemSize();  
		byte[] data=new byte[dataSize];  
		mat.get(0, 0,data);  
		int type=mat.channels()==1?  
				BufferedImage.TYPE_BYTE_GRAY:BufferedImage.TYPE_3BYTE_BGR;  

		if(type==BufferedImage.TYPE_3BYTE_BGR){  
			for(int i=0;i<dataSize;i+=3){  
				byte blue=data[i+0];  
				data[i+0]=data[i+2];  
				data[i+2]=blue;  
			}  
		}  
		BufferedImage image=new BufferedImage(mat.cols(),mat.rows(),type);  
		image.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), data);  

		return image;  
	}  

	@Override
	public void paintComponent(Graphics g){  
		if(mImg!=null){  
			g.drawImage(mImg, 0, 0, mImg.getWidth(),mImg.getHeight(),this);  
		}  
	}  

	public static void main(String[] args) {  
		try{  
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);  

			Mat capImg=new Mat();  
			VideoCapture capture=new VideoCapture(0);  
			int height = (int)capture.get(Videoio.CAP_PROP_FRAME_HEIGHT);  
			int width = (int)capture.get(Videoio.CAP_PROP_FRAME_WIDTH);  
			if(height==0||width==0){  
				throw new Exception("camera not found!");  
			}  

			JFrame frame=new JFrame("camera");  
			frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);  
			CaptureCheckFaceBasic panel=new CaptureCheckFaceBasic();
			panel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {  
					System.out.println("click");
				}
				@Override
				public void mouseMoved(MouseEvent arg0) { 
					System.out.println("move");

				}
				@Override
				public void mouseReleased(MouseEvent arg0) { 
					System.out.println("mouseReleased");
				}
				@Override
				public void mousePressed(MouseEvent arg0) { 
					System.out.println("mousePressed");
				}
				@Override
				public void mouseExited(MouseEvent arg0) { 
					System.out.println("mouseExited");
					//System.out.println(arg0.toString());
				}
				@Override
				public void mouseDragged(MouseEvent arg0) { 
					System.out.println("mouseDragged");
					//System.out.println(arg0.toString());
				}

			});
			frame.setContentPane(panel);  
			frame.setVisible(true);  
			frame.setSize(width+frame.getInsets().left+frame.getInsets().right,  
					height+frame.getInsets().top+frame.getInsets().bottom);  
			int n=0;
//			Mat temp=new Mat();
			while(frame.isShowing()&& n<500){
				//读取摄像图片
				capture.read(capImg);
				//Imgproc.cvtColor(capImg, temp, Imgproc.COLOR_RGB2GRAY);
				//Imgcodecs.imwrite("G:/opencv/lw/neg/back"+n+".png", temp);
				panel.mImg=panel.matToImg(detectFace(capImg));
				panel.repaint();
			}  
			capture.release();  
			frame.dispose();  
		}catch(Exception e){  
			System.out.println("例外：" + e);  
		}finally{  
			System.out.println("--done--");  
		}  

	}
	/**
	 * opencv实现人脸识别
	 * @param img
	 */
	public static Mat detectFace(Mat img) throws Exception
	{

		System.out.println("Running DetectFace ... ");
		// 从配置文件lbpcascade_frontalface.xml中创建一个人脸识别器，该文件位于opencv安装目录中
		CascadeClassifier faceDetector = new CascadeClassifier(openvcsrc+"\\sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml");


		// 在图片中检测人脸
		MatOfRect faceDetections = new MatOfRect();

		faceDetector.detectMultiScale(img, faceDetections);

		Rect[] rects = faceDetections.toArray();
		if(rects != null && rects.length >= 1){          
			for (Rect rect : rects) {  
				Imgproc.rectangle(img, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),  
						new Scalar(0, 0, 255), 2);  
			} 
		}
		return img;
	}


	/**
	 * opencv实现人型识别，hog默认的分类器。所以效果不好。
	 * @param img
	 */
	public static Mat detectPeople(Mat img) {
		//System.out.println("detectPeople...");
		if (img.empty()) {  
			System.out.println("image is exist");  
		}  
		HOGDescriptor hog = new HOGDescriptor();
		hog.setSVMDetector(HOGDescriptor.getDefaultPeopleDetector());
		System.out.println(HOGDescriptor.getDefaultPeopleDetector());
		//hog.setSVMDetector(HOGDescriptor.getDaimlerPeopleDetector());  
		MatOfRect regions = new MatOfRect();  
		MatOfDouble foundWeights = new MatOfDouble(); 
		//System.out.println(foundWeights.toString());
		hog.detectMultiScale(img, regions, foundWeights);        
		for (Rect rect : regions.toArray()) {             
			Imgproc.rectangle(img, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),new Scalar(0, 0, 255), 2);  
		}  
		return img;  
	} 

}
