# FallTestProgram  护理安全软件系统
***
## 主要功能 
### 老人端
（1）紧急联系人功能        
对于视力不好的老人采用相册电话簿的方式，将头像和字体放大。主要讲联系人信息存放至本地数据库，涉及数据库sqlite的增删改查的技术实现。      
（2）急救药箱       
设置、编辑吃药提醒，以闹钟的形式提醒吃药。主要涉及技术时钟Alarm机制，数据库sqlite增删改查技术。      
（3）日常监护      
主要监护老人日常行为，检测异常行为。主要涉及技术：Android手机三轴加速度传感器监听，后台服务持续运行。
### 子女端
（1）查询服务     
提供老人基本信息查询，包括心率、吃药情况、运动轨迹、家中传感器等信息。主要涉及技术网络通信，json解析。    
***
## 主要框架
volley      
CardView      
......
