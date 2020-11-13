package com.scnu.peexamsystem.service.student;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scnu.peexamsystem.dao.student.StudentDao;
import com.scnu.peexamsystem.entity.Student;
import com.scnu.peexamsystem.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Magic Gunner
 * @version 1.0
 */
@Service
public class StudentServiceImpl implements StudentService, UserDetailsService {
    @Autowired
    StudentDao studentDao;

    @Value("${upload.file.path}")
    private String filePath;
    @Value("${upload.file.size}")
    private int fileMaxSize;


    @Override
    public UserDetails loadUserByUsername(String stuNo) throws UsernameNotFoundException {
        Student student = studentDao.findStudentByStuNo(stuNo);
        if (student == null)
            throw new UsernameNotFoundException("该学生不存在");

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (student.getSubmitReason().isEmpty())
            authorities.add(new SimpleGrantedAuthority("STUDENT_NOT_SUBMIT"));
        else
            authorities.add(new SimpleGrantedAuthority("STUDENT_IS_SUBMIT"));

        return new User(student.getStuNo(), student.getPassword(), authorities);
    }

    /**
     * 查询学生列表
     *
     * @param stuName       学生姓名
     * @param instituteNo   学院号
     * @param classNo       班级号
     * @param grade         年级
     * @param status        审核状态
     * @param currentPageNo 当前页标
     * @param pageSize      每一页展示条目数
     * @return msg 返回消息
     * code 状态码，1成功，0失败
     * result 查询列表
     */
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

    /**
     * 通过学号查询单个学生
     *
     * @param stuNo 学号
     * @return msg 返回消息
     * code 状态码，1成功，0失败
     * result 学生实体
     */
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

    /**
     * 学生登录，通过stuNo和password查询是否存在该学生
     *
     * @param stuNo    学号
     * @param password 密码
     * @return msg 返回消息
     * code 状态码，1成功，0失败
     * result boolean是否登录成功
     */
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

    /**
     * 学生注册
     *
     * @param stuNo    学号
     * @param password 密码
     * @return msg 返回消息
     * code 1成功，0失败，-1已存在该用户
     */
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

    /**
     * 学生退出登录
     *
     * @param isRemoveSession 是否移除session
     * @param stuNo           学号
     * @return msg 返回消息
     * code 状态码，1成功，0失败
     * result 学号stuNo
     */
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

    /**
     * 学生提交申请
     *
     * @param student   学生实体
     * @param isSession 提交请求stuNo和session是否一致
     * @return msg 返回消息
     * code 状态码，1成功，0失败
     * result 学生实体
     */
    @Override
    public Map<String, Object> submitApplication(Student student, boolean isSession) {
        Map<String, Object> map = new HashMap<>();

        boolean isUpdate = isSession && studentDao.updateStudent(student) > 0;

        map.put("msg", "提交申请" + (isUpdate && isSession ? "成功" : "失败"));
        map.put("result", student);
        map.put("code", isUpdate ? 1 : 0);

        return map;
    }

    /**
     * 修改审核状态
     *
     * @param status 审核状态
     * @param stuNo  学号
     * @return msg 返回消息
     * code 状态码，1成功，0失败
     * result stuNo 学号，status 审核状态
     */
    @Override
    public Map<String, Object> modifyStatus(String status, String stuNo) {
        Map<String, Object> map = new HashMap<>();

        boolean isUpdate = studentDao.updateStudentVerifyStatus(status, stuNo) > 0;
        map.put("msg", "修改" + (isUpdate ? "成功" : "失败"));
        map.put("code", isUpdate ? 1 : 0);

        Map<String, Object> result = new HashMap<>();
        result.put("stuNo", stuNo);
        result.put("status", status);
        map.put("result", result);

        return map;
    }


    /**
     * 上传图片文件
     *
     * @param file  图片文件
     * @param stuNo 学号
     * @return msg 返回消息
     * code 状态码，1成功，0失败，-1格式大小有误
     */
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
