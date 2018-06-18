# <center>题目：U盘自动本地云端备份系统的设计</center>
---
### 一、 引言
    	

> 此项目想法来源于身边的许多人频繁的丢失U盘，而且U中的重要资料还没有来得及备份，造成无法挽回的后果，所以萌生了设计U盘自动备份系统的想法。项目可实现U盘插入自动备份到本机，在从本机同步到云盘的功能，对丢失或忘带U盘实现资料恢复和云端获取资料。所使用云盘为七牛云，云盘同步工具为七牛云提供qshell工具。  

#### 二、系统设计
#### 1.项目目录
 <p align="center">
    <img src="https://github.com/qxuan512/FileFly/raw/master/FileFly-UdiskBackUp/images/目录.png" width="30%">
    <br />    <small> 项目目录 </small>
</p>
</p>  

#### 2.操作流程
 <p align="center">
    <img src="https://github.com/qxuan512/FileFly/raw/master/FileFly-UdiskBackUp/images/流程.png" width="50%">
    <br />    <small> 操作流程 </small>
</p>  

#### 3.项目构成
<p align="center">
    <img src="https://github.com/qxuan512/FileFly/raw/master/FileFly-UdiskBackUp/images/构成.png" width="60%">
    <br />    <small> 项目构成 </small>
</p>  

### 四、 结论
该系统能够在用户插入U盘后，在后台自动备份U盘中的文件到本机，每天十二点定时将本地文件同步到云盘，无需用户手动备份操作，同步至云盘过程中会对本地文件和云盘文件进行文件大小，修改时间等对比，若文件相同则不进行同步。
现阶段系统云盘同步功能依赖于七牛云的qshell同步工具，所以云盘必须使用七牛云，因七牛云使用不方便，后期希望改进使用更方便的其他云盘，如微云。   

>欢迎交流QQ：982771894
