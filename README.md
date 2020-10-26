

# **华南师范大学南海校区体侧免测系统**

---

---

---

---

---

## 接口规范文档

## 学生登录

> 请求方式：POST
>
> 接口名：/student/login
>
> 说明：登录后跳到主页

### 请求参数

|   参数   | 是否必须 |    说明    |
| :------: | :------: | :--------: |
|  stuNo   |    y     |  管理员id  |
| password |    y     | 管理员密码 |

### 响应格式

```json
{
	"msg":"登录成功",
	"result":{
		"stuNo":"登录的用户ID"
	},
	"code":1
}
```

### 响应说明

|  参数  |           说明           |
| :----: | :----------------------: |
|  msg   |         提示信息         |
| result |        返回的参数        |
|  code  | 登录状态码，1成功，0失败 |



## 学生注册

> 请求方式：POST
>
> 接口名：/student/register
>
> 说明：注册后跳到登录页面

### 请求参数

|   参数   | 是否必须 |    说明    |
| :------: | :------: | :--------: |
|  stuNo   |    y     |  管理员id  |
| password |    y     | 管理员密码 |

### 响应格式

```json
{
	"msg":"注册成功",
	"result":{
		"stuID":"登录的用户ID"
	},
	"code":1
}
```

### 响应说明

|  参数  |                   说明                   |
| :----: | :--------------------------------------: |
|  msg   |                 提示信息                 |
| result |                返回的参数                |
|  code  | 登录状态码，1成功，0失败，-1已存在该用户 |

## 学生退出登录

> 请求方式：POST
>
> 接口名：/student/logout
>
> 说明：退出后跳到登录页

### 请求参数

| 参数  | 是否必须 |   说明   |
| :---: | :------: | :------: |
| stuNo |    y     | 管理员id |

### 响应格式

```json
{
	"msg":"退出登录成功",
	"result":{
		"stuID":"登录的用户ID",
	},
	"code":1
}
```

### 响应说明

|  参数  |         说明         |
| :----: | :------------------: |
|  msg   |       提示信息       |
| result |      返回的参数      |
|  code  | 状态码，1成功，0失败 |

## 管理员登录

> 请求方式：POST
>
> 接口名：/admin/login
>
> 说明：登录后跳到管理员页面

### 请求参数

|   参数   | 是否必须 |    说明    |
| :------: | :------: | :--------: |
| adminID  |    y     |  管理员id  |
| password |    y     | 管理员密码 |

### 响应格式

```json
{
	"msg":"登录成功",
	"result":{
		"adminID":"登录的用户ID",
        "adminName":"登录的用户名"
	},
	"code":1
}
```

### 响应说明

|  参数  |         说明         |
| :----: | :------------------: |
|  msg   |       提示信息       |
| result |      返回的参数      |
|  code  | 状态码，1成功，0失败 |

## 学生提交表单

> 请求方式：POST
>
> 接口名：/student/submit
>
> 说明：提交后跳转到主页

### 请求参数

|     参数      | 是否必须 |   说明   |
| :-----------: | :------: | :------: |
|     stuNo     |    y     |   学号   |
|    stuName    |    y     |   姓名   |
|    gender     |    y     |   性别   |
| submitReason  |    y     | 申请理由 |
| medicalRecord |    y     | 医院证明 |
|  instituteNo  |    y     |  学院号  |
|    classNo    |    y     |  班级号  |

### 响应格式

```json
{
	"msg":"提交成功",
	"result":{
		"stuNo":"登录的用户ID",
        "stuName":"登录的用户名",
        "gender":"性别",
        "submitReason":"理由",
        "medicalRecord":"证明",
        "instituteName":"学院名",
        "className":"班级名",
        "instituteNo":"学院号",
        "classNo":"班级号"
	},
	"code":1
}
```

### 响应参数

## 学生查询结果/管理员查看详情

>  请求方式：GET
>
> 接口名：/student/query/single

### 请求参数

| 参数  | 是否必须 | 说明 |
| :---: | :------: | :--: |
| stuNo |    y     | 学号 |

### 响应格式

```json
{
	"msg":"查询成功",
	"result":{
		"stuNo":"登录的用户ID",
        "stuName":"登录的用户名",
        "gender":"性别",
        "submitReason":"理由",
        "medicalRecord":"证明",
        "instituteName":"学院名",
        "className":"班级名",
        "instituteNo":"学院号",
        "classNo":"班级号",
        "status":"审核状态"
	},
	"code":1
}
```

### 响应参数



## 管理员查询列表

>  请求方式：GET
>
>  接口名：/student/query/list

### 请求参数

|       参数       | 是否必须 |      说明      |
| :--------------: | :------: | :------------: |
|  currentPageNo   |    y     |    当前页标    |
|     pageSize     |    y     | 每一页展示条数 |
|   queryStuName   |    n     |    学生姓名    |
| queryInstituteNo |    n     |     学院名     |
|   queryClassNo   |    n     |     班级名     |
|    queryGrade    |    n     |      年级      |
|   queryStatus    |    n     |    审核状态    |

### 响应格式

```json
{
	"msg":"查询成功",
	"result":{
        "list":[{
            "stuNo":"",
            "stuName":"",
            "gender": "",
            "instituteName":"学院",
            "grade":"年级",
            "className":"班级",
            "submitReason":"",
            "medicalRecord":"",
            "verifyStatus":""
            ]}
		}	
	},
    "page":{
        "currentPageNo":"当前页数",
        "totalPage":"总共页数",
        "totalNum":"总共条数",
        "currentNum":"当前页查询条数"
    }
	"code":1
}
```

### 响应参数

|  参数  |         说明         |
| :----: | :------------------: |
|  msg   |       提示信息       |
| result |      返回的参数      |
|  list  |      具体的列表      |
|  page  |       页码状态       |
|  code  | 状态码，1成功，0失败 |

## 管理员通过审核

>  请求方式：GET
>
>  接口名：/admin/verify

### 请求参数

|  参数  | 是否必须 |                 说明                 |
| :----: | :------: | :----------------------------------: |
| stuNo  |    y     |               学生学号               |
| status |    y     | 审核状态，1为通过审核，0为不通过审核 |

### 响应格式

```json
{
	"msg":"操作成功",
	"result":{
        "stuNo":"学生学号",
        "status":"状态码"
	},
	"code":1
}
```

### 响应参数

|  参数  |         说明         |
| :----: | :------------------: |
|  msg   |       提示信息       |
| result |      返回的参数      |
|  code  | 状态码，1成功，0失败 |

## 管理员退出登录

> 请求方式：POST
>
> 接口名：/admin/logout
>
> 说明：退出后跳到登录页

### 请求参数

无

### 响应格式

```json
{
	"msg":"退出登录成功",
	"result":{
		"stuID":"登录的用户ID",
	},
	"code":1
}
```

### 响应说明

|  参数  |         说明         |
| :----: | :------------------: |
|  msg   |       提示信息       |
| result |      返回的参数      |
|  code  | 状态码，1成功，0失败 |

## 查找学院列表

> 请求方式：GET
>
> 接口名：/institute/query

### 请求参数

无

### 响应格式

```json
{
	"msg":"退出登录成功",
	"result":[{"instituteNo":"",
              "instituteName":"",
              "classNo":""},
              {
                  <!--同上-->
              }
              ],
	"code":1
}
```

### 响应说明

|  参数  |         说明         |
| :----: | :------------------: |
|  msg   |       提示信息       |
| result |      返回的参数      |
|  code  | 状态码，1成功，0失败 |

## 查找年级列表

> 请求方式：GET
>
> 接口名：/class/query/grade

### 请求参数

无

### 响应格式

```json
{
	"msg":"查询grade成功",
	"result":[{	"grade":""},
              {<!--同上-->},
              ],
	"code":1
}
```

### 响应说明

|  参数  |         说明         |
| :----: | :------------------: |
|  msg   |       提示信息       |
| result |      返回的参数      |
|  code  | 状态码，1成功，0失败 |

## 查找班级列表

> 请求方式：GET
>
> 接口名：/class/query/list

### 请求参数

|  参数   | 是否必须 |  说明  |
| :-----: | :------: | :----: |
| classNo |    y     | 班级号 |
|  grade  |    y     |  年级  |

### 响应格式

```json
{
	"msg":"退出登录成功",
	"result":[{	"classNo":"",
              	"className":"",
        		"grade":"",
              	"instituteNo":""},
              {
                  <!--同上-->
              },
              ],
	"code":1
}
```

### 响应说明

|  参数  |         说明         |
| :----: | :------------------: |
|  msg   |       提示信息       |
| result |      返回的参数      |
|  code  | 状态码，1成功，0失败 |

