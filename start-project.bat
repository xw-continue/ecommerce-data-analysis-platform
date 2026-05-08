@echo off

rem 启动项目的批处理文件

echo ====================================
echo 电商销售数据分析平台启动脚本
echo ====================================

rem 切换到项目根目录
cd /d "%~dp0"

rem 检查Java是否安装
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo 错误：未找到Java环境，请确保已安装Java 11或更高版本
    pause
    exit /b 1
)

rem 检查Maven是否安装
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo 错误：未找到Maven环境，请确保已安装Maven
    pause
    exit /b 1
)

rem 检查Node.js是否安装
node -version >nul 2>&1
if %errorlevel% neq 0 (
    echo 错误：未找到Node.js环境，请确保已安装Node.js
    pause
    exit /b 1
)

rem 检查Python是否安装
python --version >nul 2>&1
if %errorlevel% neq 0 (
    echo 错误：未找到Python环境，请确保已安装Python 3.7或更高版本
    pause
    exit /b 1
)

rem 启动后端服务
echo 正在启动后端SpringBoot服务...
start "后端服务" cmd /k "cd backend && mvn spring-boot:run"

rem 等待后端服务启动
ping 127.0.0.1 -n 10 >nul

rem 启动前端服务
echo 正在启动前端开发服务器...
start "前端服务" cmd /k "cd frontend && npm run dev"

rem 等待前端服务启动
ping 127.0.0.1 -n 5 >nul

rem 启动Python数据处理服务
echo 正在启动Python数据处理服务...
start "Python服务" cmd /k "cd python && python main.py"

echo ====================================
echo 项目启动完成！
echo 后端服务地址: http://localhost:8081
echo 前端服务地址: http://localhost:3001
echo Python服务地址: http://localhost:5000
echo ====================================
echo 按任意键退出...
pause >nul
