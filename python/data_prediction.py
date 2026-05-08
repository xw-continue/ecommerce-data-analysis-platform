import pandas as pd
import numpy as np
from sklearn.linear_model import LinearRegression
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_squared_error, r2_score
from datetime import datetime, timedelta

def predict_sales(df):
    """预测销售额和销量"""
    print("=== 销售预测模块 ===")
    
    # 准备数据
    df['order_date'] = pd.to_datetime(df['order_date'])
    
    # 按日期分组，计算每天的销售额和销量
    daily_data = df.groupby('order_date').agg({
        'sales': 'sum',
        'quantity': 'sum'
    }).reset_index()
    
    # 按日期排序
    daily_data = daily_data.sort_values('order_date')
    
    # 特征工程：使用日期的序号作为特征
    daily_data['date_index'] = range(len(daily_data))
    
    # 准备训练数据
    X = daily_data['date_index'].values.reshape(-1, 1)
    y_sales = daily_data['sales'].values
    y_quantity = daily_data['quantity'].values
    
    # 训练销售额预测模型
    print("\n1. 训练销售额预测模型...")
    X_train, X_test, y_train, y_test = train_test_split(X, y_sales, test_size=0.2, random_state=42)
    
    model_sales = LinearRegression()
    model_sales.fit(X_train, y_train)
    
    # 测试模型
    y_pred_sales = model_sales.predict(X_test)
    mse_sales = mean_squared_error(y_test, y_pred_sales)
    r2_sales = r2_score(y_test, y_pred_sales)
    
    print(f"销售额预测模型 - MSE: {mse_sales:.2f}, R²: {r2_sales:.2f}")
    
    # 训练销量预测模型
    print("\n2. 训练销量预测模型...")
    X_train, X_test, y_train, y_test = train_test_split(X, y_quantity, test_size=0.2, random_state=42)
    
    model_quantity = LinearRegression()
    model_quantity.fit(X_train, y_train)
    
    # 测试模型
    y_pred_quantity = model_quantity.predict(X_test)
    mse_quantity = mean_squared_error(y_test, y_pred_quantity)
    r2_quantity = r2_score(y_test, y_pred_quantity)
    
    print(f"销量预测模型 - MSE: {mse_quantity:.2f}, R²: {r2_quantity:.2f}")
    
    # 预测未来30天
    print("\n3. 预测未来30天销售数据...")
    last_date = daily_data['order_date'].max()
    future_dates = [last_date + timedelta(days=i) for i in range(1, 31)]
    future_indices = [len(daily_data) + i for i in range(30)]
    
    # 预测销售额
    future_X = np.array(future_indices).reshape(-1, 1)
    future_sales_pred = model_sales.predict(future_X)
    future_quantity_pred = model_quantity.predict(future_X)
    
    # 构建预测结果
    predictions = []
    for i in range(30):
        predictions.append({
            'date': future_dates[i].strftime('%Y-%m-%d'),
            'predicted_sales': round(future_sales_pred[i], 2),
            'predicted_quantity': round(future_quantity_pred[i]),
            'confidence': round(r2_sales * 100, 2)  # 使用R²作为置信度
        })
    
    predictions_df = pd.DataFrame(predictions)
    
    print(f"预测了未来30天的销售数据")
    print("\n前5天预测结果:")
    print(predictions_df.head())
    
    # 保存预测结果
    predictions_df.to_csv('../data/prediction/sales_prediction.csv', index=False, encoding='utf-8-sig')
    
    return predictions_df

def predict_sales_arima(df, days=30):
    """使用ARIMA模型预测销售额"""
    print("\n=== ARIMA销售预测 ===")
    
    try:
        from statsmodels.tsa.arima.model import ARIMA
        
        # 准备数据
        df['order_date'] = pd.to_datetime(df['order_date'])
        daily_data = df.groupby('order_date')['sales'].sum().reset_index()
        daily_data = daily_data.set_index('order_date')
        daily_data = daily_data.asfreq('D', fill_value=0)
        
        # 训练模型
        print("训练ARIMA模型...")
        model = ARIMA(daily_data['sales'], order=(5, 1, 0))
        model_fit = model.fit()
        
        # 预测未来days天
        forecast = model_fit.forecast(steps=days)
        
        # 构建预测结果
        last_date = daily_data.index[-1]
        future_dates = pd.date_range(start=last_date + timedelta(days=1), periods=days)
        predictions = []
        
        for i, date in enumerate(future_dates):
            predictions.append({
                'date': date.strftime('%Y-%m-%d'),
                'predicted_sales': round(forecast.iloc[i], 2),
                'predicted_quantity': 0,  # 需要单独预测销量
                'confidence': 0.85  # 假设85%的置信度
            })
        
        predictions_df = pd.DataFrame(predictions)
        
        print(f"ARIMA模型预测了未来{days}天的销售数据")
        print("\n前5天预测结果:")
        print(predictions_df.head())
        
        # 保存预测结果
        predictions_df.to_csv('../data/prediction/sales_prediction_arima.csv', index=False, encoding='utf-8-sig')
        
        return predictions_df
    except ImportError:
        print("statsmodels库未安装，无法使用ARIMA模型")
        return pd.DataFrame()
    except Exception as e:
        print(f"ARIMA模型预测失败: {e}")
        return pd.DataFrame()

def main():
    """主函数"""
    # 读取清洗后的数据
    cleaned_data_path = '../data/cleaned/sales_data_cleaned.csv'
    df = pd.read_csv(cleaned_data_path, encoding='utf-8-sig')
    
    # 执行预测
    predictions = predict_sales(df)
    predict_sales_arima(df)
    
    print("\n=== 预测完成 ===")
    return predictions

if __name__ == "__main__":
    main()