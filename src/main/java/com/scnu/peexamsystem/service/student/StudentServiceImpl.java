package com.scnu.peexamsystem.service.student;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scnu.peexamsystem.dao.student.StudentDao;
import com.scnu.peexamsystem.entity.Student;
import com.scnu.peexamsystem.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentDao studentDao;

    @Value("${upload.file.path}")
    private String filePath;
    @Value("${upload.file.size}")
    private int fileMaxSize;

    @Override
    public Map<String, Object> queryStudentList(String stuName, String instituteNo, String classNo, String grade, String status,
                                                int currentPageNo, int pageSize) {

        Map<String, Object> map = new HashMap<>();

        if (!StringUtils.isEmpty(stuName))
            stuName = "%" + stuName + "%";

        String finalStuName = stuName;
        PageInfo<Student> pageInfo = PageHelper.startPage(currentPageNo, pageSize).doSelectPageInfo(new ISelect() {
            @Override
            public void doSelect() {
                studentDao.findAllByCondition(finalStuName, instituteNo, classNo, grade, status);
            }
        });


        Map<String, Object> page = new HashMap<>();
        page.put("currentPageNo", currentPageNo);
        page.put("totalPage", pageInfo.getPageNum());
        page.put("totalNum", pageInfo.getTotal());
        page.put("currentNum", pageInfo.getSize());
        map.put("page", page);


        List<Student> list = pageInfo.getList();
        map.put("msg", "查询学生列表成功");
        map.put("code", 1);
        map.put("result", list);

        return map;
    }

    @Override
    public Map<String, Object> queryStudent(String stuNo) {
        Map<String, Object> map = new HashMap<>();

        Student student = studentDao.findStudentByStuNo(stuNo);
        boolean isQuery = student != null;
        map.put("msg", "查询" + (isQuery ? "成功" : "失败"));
        map.put("result", student);
        map.put("code", isQuery ? 1 : 0);

        return map;
    }

    @Override
    public Map<String, Object> studentLogin(String stuNo, String password) {
        Map<String, Object> map = new HashMap<>();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        Student student = studentDao.findByStuNoAndPassword(stuNo, password);
        boolean isLogin = student != null;
        map.put("msg", "登录" + (isLogin ? "成功" : "失败"));
        Map<String, Object> result = new HashMap<>();
        result.put("stuNo", stuNo);
        if (isLogin)
            map.put("submit", student.getSubmitReason() != null);
        map.put("result", result);
        map.put("code", isLogin ? 1 : 0);
        return map;
    }

    @Override
    public Map<String, Object> registerStudent(String stuNo, String password) {
        Map<String, Object> map = new HashMap<>();
        boolean isExist = studentDao.existStudent(stuNo) > 0;

        if (!isExist) {
            password = DigestUtils.md5DigestAsHex(password.getBytes());
            boolean isRegister = studentDao.insertStudent(stuNo, password) > 0;
            map.put("msg", "注册" + (isRegister ? "成功" : "失败"));
            map.put("code", isRegister ? 1 : 0);
        } else {
            map.put("msg", "该用户已被注册");
            map.put("code", -1);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("stuNo", stuNo);
        map.put("result", result);

        return map;
    }

    @Override
    public Map<String, Object> studentLogout(boolean isRemoveSession, String stuNo) {
        Map<String, Object> map = new HashMap<>();

        map.put("msg", "退出登录" + (isRemoveSession ? "成功" : "失败"));
        Map<String, Object> result = new HashMap<>();
        result.put("stuNo", stuNo);
        map.put("result", result);
        map.put("code", isRemoveSession ? 1 : 0);

        return map;
    }

    @Override
    public Map<String, Object> submitApplication(Student student, boolean isSession) {
        Map<String, Object> map = new HashMap<>();

        boolean isUpdate = isSession && studentDao.updateStudent(student) > 0;

        map.put("msg", "提交申请" + (isUpdate && isSession ? "成功" : "失败"));
        map.put("result", student);
        map.put("code", isUpdate ? 1 : 0);

        return map;
    }

    @Override
    public Map<String, Object> modifyStatus(String status, String stuNo) {
        Map<String, Object> map = new HashMap<>();

        boolean isUpdate = studentDao.updateStudentVerifyStatus(status, stuNo) > 0;
        map.put("msg", "修改" + (isUpdate ? "成功" : "失败"));

        Map<String, Object> result = new HashMap<>();
        result.put("stuNo", stuNo);
        result.put("status", status);
        map.put("result", result);

        map.put("code", isUpdate ? 1 : 0);
        return map;
    }

    @Override
    public Map<String, Object> uploadImg(MultipartFile file, String stuNo) throws Exception {
        Map<String, Object> map = new HashMap<>();
        if (file == null) {
            map.put("msg", "文件为空");
            map.put("code", -1);
        } else {
            //图片文件类型
            String contentType = file.getContentType();
            if (contentType != null && (contentType.equals("image/jpg") || contentType.equals("image/png") || contentType.equals("image/jpeg"))) {
                long size = file.getSize() / 1024 / 1024;
                if (size > fileMaxSize) {
                    map.put("msg", "图片大小不能超过" + fileMaxSize + "M");
                    map.put("code", -1);
                } else {
                    //图片后缀
                    String suffix = file.getOriginalFilename().split("\\.")[1];
                    //图片名字
                    String newName = stuNo + "." + suffix;
                    //上传文件工具类
                    boolean isUpload = FileUtil.uploadFile(file.getBytes(), filePath + stuNo, "/" + newName);
                    map.put("msg", "图片上传" + (isUpload ? "成功" : "失败"));
                    map.put("code", isUpload ? 1 : 0);
                }
            } else {
                map.put("msg", "请上传jpg、png、jpeg格式的文件");
                map.put("code", -1);
            }
        }

        return map;
    }


}
