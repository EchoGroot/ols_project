package com.ols.ols_project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baidu.fsg.uid.service.UidGenService;
import com.ols.ols_project.common.Const.NormalConst;
import com.ols.ols_project.common.utils.FileUtils;
import com.ols.ols_project.common.utils.SendEmailBy126;
import com.ols.ols_project.common.utils.XmlUtil;
import com.ols.ols_project.common.utils.ZipUtils;
import com.ols.ols_project.mapper.TaskMapper;
import com.ols.ols_project.model.*;
import com.ols.ols_project.model.entity.AcceptEntity;
import com.ols.ols_project.model.entity.UserEntity;
import com.ols.ols_project.service.TaskService;
import com.ols.ols_project.service.UserService;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.nio.file.Files.copy;

/**
 * 关于任务的Controller
 * @author yuyy
 * @date 20-2-24 下午7:03
 */

@RestController
@RequestMapping("task")
public class TaskController {
    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private SendEmailBy126 sendEmailBy126;

    @Autowired
    private UidGenService uidGenService;

    @Value("${image.fileFath}")
    private String desFilePath;

    @Value("${image.thumbFilePath}")
    private String thumbFilePath;

    /**
     * 不知道这方法还在用没，先注释掉，如果项目正常运行，就删掉这个方法
     * 根据任务ID获取图片信息（ols_task表）
     * @param taskId
     * @return
     */
//    @GetMapping("/getImageListByTaskId")
//    public String getImageListByTaskId(
//            @RequestParam("taskId") int taskId,
//        @RequestParam("userId") int userId
//    ){
//        log.info("用户ID：{}，");
//        HashMap<String,Object> data=new HashMap<>();
//        data.put("taskImage",taskService.getImageListByTaskId(taskId));
//        return JSON.toJSONString(new Result(data,"200","获取任务图片数据成功"));
//    }

    /**
     * 根据接受任务ID获取图片信息（ols_accept表）
     * @param acceptId
     * @return
     */
    @GetMapping("/getAcceptImageListByAcceptId")
    public String getAcceptImageListByAcceptId(
            @RequestParam("acceptId") String acceptId,
            @RequestParam("userId") String userId
    ){
        String resultStr=null;
        HashMap<String,Object> data=new HashMap<>();
        data.put("taskImage",
                taskService.getAccepteImageListByAccepteId(Long.parseLong(acceptId)));
        resultStr= JSON.toJSONStringWithDateFormat(
                new Result(data,"200","获取接受任务图片数据成功"),
                "yyyy-MM-dd hh:mm:ss");
        return resultStr;
    }

    /**
     * 根据任务ID获取任务信息（ols_task表）
     * @param
     * @return
     */
    @GetMapping("/getTaskInfoByTaskId")
    public String getTaskInfoByTaskId(
            @RequestParam("taskId") String taskId,
            @RequestParam("userId") String userId
    ){
        String resultStr=null;
        HashMap<String,Object> data=new HashMap<>();
        data.put("taskInfo",
                taskService.getTaskInfoByTaskId(Long.parseLong(taskId)));
        resultStr= JSON.toJSONStringWithDateFormat(
                new Result(data,"200","获取任务数据成功"),
                "yyyy-MM-dd hh:mm:ss");
        return resultStr;
    }

    /**
     * 存储图片标注信息
     *
     * @return
     */
    @PostMapping(value = "/storeImageLabelInfo")
    public String storeImageLabelInfo(
            @RequestParam("pageType") String pageType,
            @RequestParam("userId") String userId,
            @RequestParam("acceptId") String acceptId,
            @RequestParam("taskId") String taskId,
            @RequestParam("imageUrlParam") String imageUrlParam,
            @RequestParam("labelInfo") String labelInfo

    ){
        String resultStr=null;
        List<LabelInfo> labelInfoList= (List<LabelInfo>)JSON.parse(labelInfo);
        long tempTaskId=0L;
        if("labelExamplePage".equals(pageType)){
            tempTaskId=Long.parseLong(taskId);
        }else{
            tempTaskId=Long.parseLong(acceptId);
        }
        if(1==taskService.storeImageLabelInfo(
                pageType,
                tempTaskId,
                labelInfoList,
                imageUrlParam)){
            resultStr= JSON.toJSONString(
                    new Result("200","存储图片标注数据成功"));
        }else{
            resultStr=JSON.toJSONString(
                    new Result("201","存储图片标注数据失败"));
        }
        return resultStr;
    }

    @PostMapping(value = "/setTaskStateByTaskId")
    public String setTaskStateByTaskId(@RequestParam("taskId") String taskId){
        String resultStr=null;
        if(StringUtils.isNotBlank(taskId)){
            if(1==taskService.setTaskStateByTaskId(Long.parseLong(taskId))){
                resultStr= JSON.toJSONString(
                        new Result("200","存储标注示例成功"));
            }else{
                resultStr= JSON.toJSONString(
                        new Result("201","存储标注示例失败"));
            }
        }else{
            resultStr= JSON.toJSONString(
                    new Result("202","参数不能为空"));
        }
        return resultStr;
    }

    /**
     * 获取待审核的任务
     * userId：审核者账号
     * @param userId
     * @return
     */
    @PostMapping(value = "/getNotCheckedTask")
    public String getNotCheckedTask(@RequestParam("userId") String userId){
        String resultStr=null;
        //第一次查询，查的是已分配给该审核者的
        List<TaskEntityBo> notCheckedTask = taskService.getNotCheckedTask(Long.parseLong(userId));
        if(notCheckedTask.size() < NormalConst.SET_NOT_CHECKED_TASK_FOR_USER_COUNT){
            //分配任务给该审核者
            taskService.setNotCheckedTaskForUser(
                    Long.parseLong(userId),
                    NormalConst.SET_NOT_CHECKED_TASK_FOR_USER_COUNT-notCheckedTask.size());
            //第二次查询
            notCheckedTask = taskService.getNotCheckedTask(Long.parseLong(userId));
        }
        HashMap<String,Object> data=new HashMap<>();
        data.put("taskList",notCheckedTask);
        resultStr=JSON.toJSONStringWithDateFormat(
                new Result(data,"0","获取待审核任务成功"),
                "yyyy-MM-dd hh-mm-ss");
        return resultStr;
    }

    /**
     * 接受任务
     * @param userId 用户id
     * @param taskId 任务id（ols_task表）
     * @return
     */
    @PostMapping(value = "/acceptTask")
    public String acceptTask(
            @RequestParam("userId") String userId,
            @RequestParam("taskId") String taskId
                             ){
        String resultStr=null;
        //查询该用户是否已接受此任务
        List<AcceptTaskBo>acceptTaskByUserId=
                (List<AcceptTaskBo>)userService.getAcceptTaskByUserId(Long.parseLong(userId),"",0,0,"","","").get("taskList");
        for(AcceptTaskBo item:acceptTaskByUserId){
            if(item.getTaskId()==Long.parseLong(taskId)){
                resultStr=JSON.toJSONString(
                        new Result("201","该用户已接受此任务"));
                break;
            }
        }
        if(resultStr==null){
            if(2==taskService.acceptTask(Long.parseLong(userId),Long.parseLong(taskId))){
                resultStr=JSON.toJSONString(
                        new Result("200","接受任务成功"));
            }else{
                resultStr=JSON.toJSONString(
                        new Result("202","接受任务失败"));
            }
        }
        return resultStr;
    }

    /**
     * 任务是否通过审核
     * @param userId
     * @param taskId
     * @param operation
     * @param message
     * @return
     */
    @PostMapping(value = "/taskPassOrNotPassAudits")
    public String taskPassOrNotPassAudits(
            @RequestParam("userId") String userId,
            @RequestParam("taskId") String taskId,
            @RequestParam("operation") String operation,
            @RequestParam("message") String message
                             ){
        String resultStr=null;
        if(1==taskService.taskPassOrNotPassAudits(
                Long.parseLong(userId),
                Long.parseLong(taskId),
                operation,
                message
                )){
            UserEntity userInfo = userService.getUserInfoById(Long.parseLong(userId));
            //发送邮件通知
            sendEmailBy126.sendEmail(
                    userInfo.getEmail()
                    ,"Ols系统通知"
                    ,message);
            resultStr=JSON.toJSONString(new Result("200","操作成功"));

        }else{
            resultStr=JSON.toJSONString(new Result("201","操作失败，请刷新再试"));
        }
        return resultStr;
    }

    /**
     * 根据审核者id获取已审核的任务
     * @param pageNum
     * @param pageSize
     * @param userId
     * @param queryInfo 筛选条件
     * @param searchInfo 搜索关键字（任务名）
     * @return
     */
    @GetMapping("/getFinishCheckTaskByUserId")
    public String getFinishCheckTaskByUserId(
            @RequestParam("page") int pageNum,
            @RequestParam("limit") int pageSize,
            @RequestParam("userId") String userId,
            @RequestParam(value = "queryInfo") String  queryInfo,
            @RequestParam(value = "searchInfo") String  searchInfo
    ){
        // layui默认数据表格的status为0才显示数据
        String result=JSON.toJSONStringWithDateFormat(
                new Result(taskService.getFinishCheckTaskByUserId(Long.parseLong(userId),queryInfo,searchInfo,pageNum, pageSize),"0","根据审核者id获取已审核的任务"),
                "yyyy-MM-dd");
        return result;
    }

    /**
     * 提交已接受的任务
     * @param userId
     * @param acceptId
     * @param taskId
     * @return
     */
    @PostMapping(value = "/submitAcceptTask")
    public String submitAcceptTask(
            @RequestParam("userId") String userId,
            @RequestParam("acceptId") String acceptId,
            @RequestParam("taskId") String taskId
    ){
        String resultStr=null;
        if(2==taskService.submitAcceptTask(Long.parseLong(acceptId),Long.parseLong(taskId))){
            resultStr= JSON.toJSONString(
                    new Result("200","提交已接受的任务成功"));
        }else{
            resultStr=JSON.toJSONString(
                    new Result("201","提交已接受的任务失败"));
        }
        return resultStr;
    }

    /**
     *
     * @author wjp
     * @date 2020/3/21 23.30
     */
    @PostMapping("/createTask")
    public String createTask( String taskName,String taskUrl, String taskInfo, int rewardPoints, int type, Long releaseUserId){
        String resultStr;
        if(taskService.deductRewardPoints(rewardPoints,releaseUserId)==1){
            String result = taskService.creatTask(taskName,taskUrl,taskInfo,rewardPoints, type,releaseUserId);
            HashMap<String,Object> data = new HashMap();
            data.put("taskId",result);
            resultStr = JSON.toJSONString(new Result(data,"1","积分扣除成功"));
        }else{
            resultStr = JSON.toJSONString(new Result("0","积分不足！发布失败"));
        }
        return resultStr;
    }

    @PostMapping("/creatTaskUrl")
    public String creatTaskUrl(String labelName, String originalImage){
        return taskService.creatTaskUrl(labelName,originalImage);
    }
    @PostMapping("/creatDocTaskUrl")
    public String creatDocTaskUrl(String labelName, String originalDoc){
        return taskService.creatDocTaskUrl(labelName,originalDoc);
    }
    @PostMapping("uploadImgs")
    public String uploadImgs(@RequestParam("file") MultipartFile file) {
        String oriName = "";
        String staticPath=desFilePath;
        String thumbPath = thumbFilePath;
        try {
            // 1.获取原文件名
            oriName = file.getOriginalFilename();
            // 2.获取原文件图片后缀名extensionName，以最后的.作为截取(.jpg)
            String extName = oriName.substring(oriName.lastIndexOf("."));
            // 3.生成自定义的新文件名，这里以UUID.jpg|png|xxx作为格式（可选操作，也可以不自定义新文件名）
            //String uuid = UUID.randomUUID().toString();
            long uuid = uidGenService.getUid();//生成通用唯一识别码
            String newName = uuid + extName;
            //String realPath = request.getRealPath("http://yuyy.info/image/ols/");
            // 4.保存绝对路径
            staticPath += newName;
            File desFile = new File(staticPath);
            file.transferTo(desFile);
            // 5.产生缩略图
            thumbPath += newName;
            Thumbnails.of(staticPath).size(NormalConst.THUMB_WIDTH, NormalConst.THUMB_HEIGHT).keepAspectRatio(false).toFile(thumbPath);
            // 6.返回保存结果信息
            HashMap<String,Object> dataMap=new HashMap<>();
            dataMap.put("src", "/图片保存的绝对路径地址/" + newName);
            dataMap.put("imgName", newName);
            Result result = new Result(dataMap,"0","上传成功");
            System.out.println(staticPath+"图片保存成功");
            return JSON.toJSONString(result);
        } catch (IllegalStateException e) {
            Result result = new Result("1","图片保存失败");
            System.out.println(staticPath + "图片保存失败");
            return JSON.toJSONString(result);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            Result result = new Result("1","图片保存失败--IO异常");
            System.out.println(staticPath + "图片保存失败--IO异常");
            return JSON.toJSONString(result);
        }
    }
    @PostMapping("uploadDocs")
    public String uploadDocs(@RequestParam("file") MultipartFile file) {
        String staticPath=desFilePath;
        try {
            // 2.获取原文件后缀名extensionName，以最后的.作为截取(.txt .doc .pdf)
            String extName =file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            // 3.生成自定义的新文件名
            String newName = uidGenService.getUid() + extName;
            //String realPath = request.getRealPath("http://yuyy.info/image/ols/");
            // 4.保存绝对路径
            staticPath += newName;
            File desFile = new File(staticPath);
            file.transferTo(desFile);
            // 6.返回保存结果信息
            HashMap<String,Object> dataMap=new HashMap<>();
            dataMap.put("src", "/文件保存的绝对路径地址/" + newName);
            dataMap.put("docName", newName);
            Result result = new Result(dataMap,"0","上传成功");
            System.out.println(staticPath+"文件保存成功");
            return JSON.toJSONString(result);
        } catch (IllegalStateException e) {
            Result result = new Result("1","文件保存失败");
            System.out.println(staticPath + "文件保存失败");
            return JSON.toJSONString(result);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            Result result = new Result("1","文件保存失败--IO异常");
            System.out.println(staticPath + "文件保存失败--IO异常");
            return JSON.toJSONString(result);
        }
    }
    @GetMapping("/getAllTask")
    public String getAllTask(@RequestParam(value = "query") String query,
                             @RequestParam(value = "page") Integer pageNum,
                             @RequestParam(value = "limit") Integer pageSize,
                             @RequestParam(value = "queryInfo") String queryInfo,
                             @RequestParam(value = "searchType") String searchType,
                             @RequestParam(value = "searchInfo") String searchInfo,
                             @RequestParam(value = "field") String field,
                             @RequestParam(value = "order") String order){
        String result= JSON.toJSONStringWithDateFormat(
                new Result(
                        taskService.getAllTask(query, pageNum, pageSize,queryInfo,searchType,searchInfo,field,order)
                        ,"0"
                        ,"获取所有任务成功")
                ,"yyyy-MM-dd hh:mm:ss"
                ,SerializerFeature.WriteNonStringValueAsString);
        return result;
    }
    @PostMapping("/clickNumPlus")
    public String clickNumPlus(@RequestParam(value = "taskId") long taskId){
        taskService.clickNumPlus(taskId);
        return "点击量+1！";
    }
    @GetMapping("/getClickNum")
    public String getClickNum(){
        return JSON.toJSONString(taskService.getClickNum(),SerializerFeature.WriteNonStringValueAsString);
    }
    //弃用
    @GetMapping("/getDownLoadAddress")
    public String getDownLoadAddress(@RequestParam(value = "taskId") long taskId){
        JSONArray fileNameArray = taskService.getFileNameByTaskId(taskId);
        List fileAddress =  new ArrayList<>();
        String url = "http://yuyy.info/image/ols/";
        for(int i=0;i<fileNameArray.size();i++){
            fileAddress.add(url+fileNameArray.get(i));
        }
        //返回图片地址  标注信息
        HashMap<String,Object> data=new HashMap<>();
        data.put("url", fileAddress);
        data.put("labelInfo",taskService.getImageListByTaskId(taskId));
        Result result = new Result(data,"0","下载信息成功获取");
        return JSON.toJSONString(result);
    }
    @GetMapping("/downloadFinishedTask")
    public void downloadFT(@RequestParam(value = "taskId") long taskId,
                           HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断环境
        String str="";
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            str = "\\";
        }else if(os.toLowerCase().startsWith("lin")){
            str = "/";
        }

        JSONArray fileNameArray = taskService.getFileNameByTaskId(taskId);
        String path = desFilePath;
        //String path = "http:\\\\yuyy.info\\image\\ols\\";//linux 路径有点问题
        //新建临时文件夹用于打包所有要下载的问文件 若已存在，则先删除再创建
        File fileAllTemp = new File(path+taskId);
        if (fileAllTemp.exists()) {
            FileUtils.deleteFile(fileAllTemp);//遍历删除文件夹及其子内容
            fileAllTemp.mkdirs();
        }else {
            fileAllTemp.mkdirs();
        }
        for(int i=0;i<fileNameArray.size();i++){
            File fromFile = new File(path+fileNameArray.get(i));//找到文件
            File toFile = new File(path+taskId+str+fileNameArray.get(i));//目标文件地址 用于将要打包的文件放在一起
            copy(fromFile.toPath(),toFile.toPath());//将源文件复制进临时文件夹  用于打包
        }

        //将采纳的标注信息以xml格式也加入打包文件中
        AcceptEntity acceptEntity = taskMapper.getAccepteTaskInfoByAcceptId(taskMapper.getTaskInfoByTaskId(taskId).getAdopt_accept_id());
        AcceptImageUrl acceptImageUrl = JSON.parseObject(acceptEntity.getUrl(), new TypeReference<AcceptImageUrl>() {});
        FileUtils.saveAsFileWriter(XmlUtil.convertToXml(acceptImageUrl),fileAllTemp.getPath()+str+taskId+"labelInfo.xml");
        //FileUtils.saveAsFileWriter(taskService.getImageListByTaskId(taskId),fileAllTemp.getPath()+"\\"+taskId+"labelInfo.xml");

        FileOutputStream zip = new FileOutputStream(new File(fileAllTemp.getPath()+".zip"));
        ZipUtils.toZip(fileAllTemp.getPath(),zip,true);

        File zipFile = new File(fileAllTemp.getPath()+".zip");//选中压缩文件
        if(zipFile.exists())
        {
            response.setContentType("application/x-msdownload"); //设置响应类型,此处为下载类型
            response.setHeader("Content-Disposition", "attachment;filename=\""+taskId+".zip\"");//以附件的形式打开
            InputStream inputStream = new FileInputStream(zipFile);
            ServletOutputStream outputStream = response.getOutputStream();
            byte b[] = new byte[1024];
            int n;
            while((n = inputStream.read(b)) != -1)
            {
                outputStream.write(b,0,n);
            }
            outputStream.close();
            inputStream.close();
        }else{
            request.setAttribute("result", "文件不存在！下载失败！");
            //request.getRequestDispatcher("/fildeOp.jsp").forward(request, response);
        }
        FileUtils.deleteFile(fileAllTemp);//临时文件夹用完删除
        zipFile.delete();//临时压缩包用后删除
    }

    @GetMapping("/getAllReleaseById")
    public String getAllReleaseById(
            @RequestParam("year") String year,
            @RequestParam("userId") String userId
    ){
        HashMap<String, Object> data = new HashMap<>();
        data.put("releaseList",
                taskService.getAllReleaseById(Long.parseLong(userId),Integer.parseInt(year)));
        String result= JSON.toJSONStringWithDateFormat(
                new Result(data,"200","获取个人已发布成功"),
                "yyyy-MM-dd");
        return result;
    }

    @GetMapping(value = "/getAcceptImgTaskByUserId")
    public String getAcceptImgTaskByUserId(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "query") String query,
            @RequestParam(value = "page") Integer pageNum,
            @RequestParam(value = "limit") Integer pageSize,
            @RequestParam(value = "queryInfo") String queryInfo,
            @RequestParam(value = "searchInfo") String searchInfo,
            @RequestParam(value = "field") String field,
            @RequestParam(value = "order") String order
    ) {
        String result = JSON.toJSONStringWithDateFormat(
                new Result(
                        taskService.getAcceptImgTaskByUserId(
                                Long.parseLong(userId), query, pageNum, pageSize, queryInfo, searchInfo,field,order)
                        , "0", "获取已接受任务成功")
                , "yyyy-MM-dd hh:mm:ss"
                , SerializerFeature.WriteNonStringValueAsString
        );
        return result;
    }

    @GetMapping(value = "/getReleaseImgTaskByUserId")
    public String getReleaseImgTaskByUserId(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "query") String query,
            @RequestParam(value = "page") Integer pageNum,
            @RequestParam(value = "limit") Integer pageSize,
            @RequestParam(value = "queryInfo") String queryInfo,
            @RequestParam(value = "searchInfo") String searchInfo,
            @RequestParam(value = "field") String field,
            @RequestParam(value = "order") String order
    ) {
        // layui默认数据表格的status为0才显示数据
        String result = JSON.toJSONStringWithDateFormat(
                new Result(
                        taskService.getReleaseImgTaskByUserId(Long.parseLong(userId), query, pageNum, pageSize, queryInfo, searchInfo,field,order)
                        , "0"
                        , "获取已发布任务成功")
                , "yyyy-MM-dd hh:mm:ss"
                , SerializerFeature.WriteNonStringValueAsString
        );
        return result;
    }
}
