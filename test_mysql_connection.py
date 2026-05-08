import mysql.connector

def test_connection():
    """测试MySQL连接"""
    print("=== MySQL连接测试 ===")
    
    # 测试不同的密码组合
    passwords = ['', '123456', 'root', 'mysql', 'password', 'admin']
    
    for password in passwords:
        print("\n尝试密码: '" + password + "'")
        try:
            conn = mysql.connector.connect(
                host='localhost',
                user='root',
                password=password,
                database='ecommerce_analysis'
            )
            print("连接成功！")
            print("  主机: " + conn.get_server_info())
            print("  数据库: " + conn.database)
            conn.close()
            return password
        except Exception as e:
            print("连接失败: " + str(e))
    
    # 尝试创建数据库
    print("\n尝试创建数据库...")
    for password in passwords:
        print("尝试密码: '" + password + "'")
        try:
            conn = mysql.connector.connect(
                host='localhost',
                user='root',
                password=password
            )
            cursor = conn.cursor()
            cursor.execute("CREATE DATABASE IF NOT EXISTS ecommerce_analysis")
            print("数据库创建成功")
            cursor.close()
            conn.close()
            return password
        except Exception as e:
            print("数据库创建失败: " + str(e))
    
    return None

def main():
    """主函数"""
    success_password = test_connection()
    if success_password:
        print("\n成功找到可用的密码: '" + success_password + "'")
        print("请更新以下文件中的密码配置:")
        print("1. python/db_connection.py")
        print("2. backend/src/main/resources/application.yml")
    else:
        print("\n所有连接尝试都失败了")
        print("请检查MySQL服务是否启动，以及root用户的密码设置")
        print("\n如果您不知道MySQL root密码，可以尝试以下方法:")
        print("1. 重置MySQL root密码")
        print("2. 创建一个新的MySQL用户并授权")
        print("3. 使用phpMyAdmin或其他MySQL管理工具来管理数据库")

if __name__ == "__main__":
    main()