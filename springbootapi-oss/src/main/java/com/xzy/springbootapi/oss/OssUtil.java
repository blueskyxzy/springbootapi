package com.xzy.springbootapi.oss

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*
 * Created by xzy on 2017/11/21
 */

@Component
public class OssUtil {

    private static final Logger logger = LoggerFactory.getLogger(OssUtil.class);
    public static final String FORMAT_NAME_PNG = "png"; // cannot be compressed
    public static final String FORMAT_NAME_JPG = "jpg";
    public static final String FORMAT_NAME_JPEG = "jpeg"; // same as jpg
    public static final String FORMAT_NAME_BMP = "bmp";

    private static String endpoint;
    @Value("${oss.endpoint}")
    public void setEndpoint(String endpoint) {
        OssUtil.endpoint = endpoint;
    }

    private static String accessKeyId;
    @Value("${oss.accessKeyId}")
    public void setAccessKeyId(String accessKeyId) {
        OssUtil.accessKeyId = accessKeyId;
    }

    private static String accessKeySecret;
    @Value("${oss.accessKeySecret}")
    public void setAccessKeySecret(String accessKeySecret) {
        OssUtil.accessKeySecret = accessKeySecret;
    }

    private static String bucketName;
    @Value("${oss.bucketName}")
    public void setBucketName(String bucketName) {
        OssUtil.bucketName = bucketName;
    }


    /*上传文件到OSS并返回文件的URL
      参数 picFile: 需要上传的文件 filePrefix:上传到OSS的文件前缀
    */
    public static String upLoadFile(MultipartFile picFile,String filePrefix)throws IOException {
        return upLoadFile(picFile,filePrefix,null,null);
    }

//    public static String upLoadFileWithZip(String sourceFile,String filePrefix, ){
//
//        ImageUtil.compressPic();
//    }


    public static String upLoadFile(MultipartFile file,String filePrefix,Integer maxWidth,Integer maxHeight)throws IOException {
        /*  根据你的bucket填入对应的信息*/
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {

            logger.debug("upload file to oss start ...");

            //根据模块前缀加时间戳加文件名后缀作为key
            Calendar cal = Calendar.getInstance();
            Long timestamp = cal.getTimeInMillis();
            String fileName = file.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            String key = filePrefix + "/" + String.valueOf(timestamp) + suffix;

            //上传文件
            InputStream fileInputStream;
            byte [] bytes;
            if((maxWidth == null || maxWidth <= 0) && (maxHeight == null || maxHeight <=0)) {
                fileInputStream = file.getInputStream();
            } else {
                bytes = thumbnailImage(file, maxWidth, maxHeight);
                fileInputStream = new ByteArrayInputStream(bytes);
            }
            PutObjectResult putObjectResult = client.putObject(bucketName, key, fileInputStream);
            logger.debug("putObjectResult: {}",JsonUtils.toJson(putObjectResult));

            // 设置URL过期时间为10年  3600l* 1000*24*365*10
            Date expiration = new Date(new Date().getTime() + 3600L * 1000 * 24 * 365 * 10);
            URL url = client.generatePresignedUrl(bucketName, key, expiration);
            logger.debug("upload fileName: {},key: {},url：{}",fileName,key,url.toString());
            if (url == null){
                throw new IOException("图片上传失败");
            }
            return url.toString();
           /*String url = visitHost + key;
           return url;*/

        } catch (OSSException oe) {
            logger.error("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.Error Message:"+oe.getErrorCode()
                    + ",Request ID: {}"+oe.getRequestId()+",Host ID: {}"+oe.getHostId(),oe);
        } catch (ClientException ce) {
            logger.error("Caught an ClientException,Error Message: "+ ce.getMessage(),ce);
        } finally{
            client.shutdown();
        }
        return null;
    }

    //根据keys删除对象
    public static boolean deleteObject(List<String> keys) {
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        try {
            logger.debug("delete object from oss start ...");
            logger.debug("delete object bucketName : {},keys : {}",bucketName,JsonUtils.toJson(keys));
            DeleteObjectsResult deleteObjectsResult = client.deleteObjects(
                    new DeleteObjectsRequest(bucketName).withKeys(keys));
            List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
            logger.debug("successfully deleted objects: {}",JsonUtils.toJson(deletedObjects));
            return true;
        } catch (OSSException oe) {
            logger.error("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.Error Message:"+oe.getErrorCode()
                    + ",Request ID: {}"+oe.getRequestId()+",Host ID: {}"+oe.getHostId(),oe);
            return false;
        } catch (ClientException ce) {
            logger.error("Caught an ClientException,Error Message: "+ ce.getMessage(),ce);
            return false;
        } finally{
            client.shutdown();
        }
    }

    //根据完整的url删除文件
    public static boolean deleteFile(String origPicUrl) {
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        try {
            logger.debug("delete object from oss start ...");
            if (origPicUrl==null){
                logger.error("delete file origPicUrl is null");
                return true;
            }
            if (!origPicUrl .startsWith("http://sw-prduct.oss-cn-beijing.aliyuncs.com")){
                logger.debug("origPicUrl is not an oss url,origPicUrl: {}",origPicUrl);
                return true;
            }
            //截取url中的key
            int start = origPicUrl.indexOf(".com/");
            int end = origPicUrl.indexOf("?Expires=");
            String key = origPicUrl.substring(start+5,end);
            logger.debug("origPicUrl: {},key: {}",origPicUrl,key);

            //根据key删除文件
            client.deleteObject(bucketName,key);
            logger.debug("successfully delete file ,origPicUrl:{}",origPicUrl);
            return true;
        } catch (OSSException oe) {
            logger.error("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.Error Message:"+oe.getErrorCode()
                    + ",Request ID: {}"+oe.getRequestId()+",Host ID: {}"+oe.getHostId(),oe);
            return false;
        } catch (ClientException ce) {
            logger.error("Caught an ClientException,Error Message: "+ ce.getMessage(),ce);
            return false;
        } finally{
            client.shutdown();
        }

    }

    //图片过大将按比例缩放
    public static byte[] thumbnailImage(InputStream is, Integer maxWidth, Integer maxHeight) throws IOException {
        if (is == null) {
            throw new IllegalArgumentException("InputStream cannot be null.");
        }
        BufferedImage image = ImageIO.read(is);
        int width = image.getWidth();
        int height = image.getHeight();
        logger.debug("Image's original width is {}, height is {}.", width, height);
        double scale = 1d;

        //判断图片宽高的缩放比例
        if (maxWidth != null && maxWidth > 0) {
            if (maxWidth < width) {
                scale = maxWidth / (double) width;
            }
        }
        if (maxHeight != null && maxHeight > 0) {
            if (maxHeight < height) {
                double hScale = maxHeight / (double) height;
                if (hScale < scale) {
                    scale = hScale;
                }
            }
        }

        // will not scale if the width or height is nearly equal to target
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(image).scale(scale).outputFormat(FORMAT_NAME_JPG).toOutputStream(outputStream);
        return outputStream.toByteArray();
    }

    //根据不同条件将file转成字节
    public static byte[] thumbnailImage(MultipartFile file, Integer maxWidth, Integer maxHeight) throws IOException {
        logger.debug("thumbnailImage start ...");
        if (file == null) {
            return null;
        }
        String filename = file.getOriginalFilename();
        if (filename == null) {
            return file.getBytes();
        }
        String lowerFilename = filename.toLowerCase().trim();
        if (!lowerFilename.endsWith(FORMAT_NAME_JPG) && !lowerFilename.endsWith(FORMAT_NAME_JPEG) && !lowerFilename.endsWith(FORMAT_NAME_PNG) && !lowerFilename.endsWith(FORMAT_NAME_BMP)) {
            return file.getBytes();
        }
        return thumbnailImage(file.getInputStream(), maxWidth, maxHeight);
    }


}
