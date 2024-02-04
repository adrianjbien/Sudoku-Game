import math

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt


def average(col, numbers):
    avg = 0.0
    sum = 0.0
    for value in col:
        sum += value

    avg = sum/numbers
    return avg


def variance(col, numbers):
    varian = 0.0
    for var in range(len(col)):
        varian += (col[var] - average(col, numbers)) ** 2
    varian /= (numbers - 1)
    return varian


def covariance(colX, colY, numbers):
    cov = 0.0
    for i in range(numbers):
        cov += (colX[i] - average(colX, numbers)) * (colY[i] - average(colY, numbers))
    cov /= (numbers - 1)
    return cov


def standard_deviation(col, numbers):
    # result = 0.0
    # for i in range(len(col)):
    #     result += (col[i] - average(col,numbers)) ** 2
    # result /= (numbers - 1)
    # standard = result ** 0.5
    standard = variance(col, numbers) ** 0.5
    return standard


def pearson(colX, colY, numbers):
    div = standard_deviation(colX, numbers) * standard_deviation(colY, numbers)
    cov = covariance(colX,colY,numbers)
    result = cov / div
    return result


def tangens(colX, colY, numbers):
    result = covariance(colX,colY,numbers) / variance(colX,numbers)
    return result


def y_intercept(colX, colY, numbers):
    result = average(colY, numbers) - tangens(colX, colY, numbers) * average(colX, numbers)
    return result


def f(x, a, b):
    return a * x + b


def show_graph(colX, colY, numbers, xlabel, ylabel):
    start = math.floor(min(colX)) - 0.1
    end = math.ceil(max(colX))
    plt.rcParams.update({'font.size': 16})
    plt.figure(figsize=(8, 6))
    plt.scatter(colX, colY, s=100)
    variable = y_intercept(colX, colY, numbers)
    if variable > 0:
        sign = " + "
    else:
        variable *= -1
        sign = " - "

    title = ("r = " + "%.2f" % pearson(colX, colY, numbers) + "; y = " + "%.1f" % tangens(colX, colY, numbers) + "x" +
             sign + "%.1f" % variable)
    plt.title(title)
    plt.xlabel(xlabel)
    plt.ylabel(ylabel)
    x = np.linspace(start, end, 10)
    plt.plot(x, f(x, tangens(colX, colY, numbers), y_intercept(colX, colY, numbers)), c="red")
    plt.show()


def aggregation(colX, colY, numbers, xlabel, ylabel):
    print("współczynnik korelacji liniowej Pearson: " + str(pearson(colX, colY, numbers)))
    print("wspolczynnik b: " + str(y_intercept(colX, colY, numbers)))
    print("wspolczynnik a: " + str(tangens(colX, colY, numbers)) + "\n")
    show_graph(colX, colY, numbers, xlabel, ylabel)


def read_file():
    data = pd.read_csv("data.csv", header=None)
    last_columns = data.iloc[:, -1]
    first_column = data.iloc[:, 0]
    second_column = data.iloc[:, 1]
    third_column = data.iloc[:, 2]
    fourth_column = data.iloc[:, 3]
    gatunek0 = 0
    gatunek1 = 0
    gatunek2 = 0
    for column in last_columns:
        if column == 0:
            gatunek0 = gatunek0 + 1
        elif column == 1:
            gatunek1 = gatunek1 + 1
        elif column == 2:
            gatunek2 = gatunek2 + 1

    result = gatunek0 + gatunek1 + gatunek2

    print("liczebność setosa: " + str(gatunek0) + "\n" + "liczebnosc versicolor: " + str(gatunek1) + "\n" + "liczebnosc virginica: " + str(gatunek2) + "\n")

    ddk = "Długość działki kielicha (cm)"
    sdk = "Szerokość działki kielicha (cm)"
    dp = "Długość płatka (cm)"
    sp = "Szerokość płatka (cm)"

    aggregation(first_column, second_column, result, ddk, sdk)
    aggregation(first_column, third_column, result, ddk, dp)
    aggregation(first_column, fourth_column, result, ddk, sp)
    aggregation(second_column, third_column, result, sdk, dp)
    aggregation(second_column, fourth_column, result, sdk, sp)
    aggregation(third_column, fourth_column, result, dp, sp)


read_file()
