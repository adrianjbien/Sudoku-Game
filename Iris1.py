import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import warnings


warnings.filterwarnings("ignore")


def view_first_table(counter0, counter1, counter2, result):
    fig, ax = plt.subplots()
    data = [
    ["Gatunek", "Liczebnosc(%)"],
    ["setosa", str(counter0) + "(" + str("%.1f" % (counter0 * 100 / result)) + "%)"],
    ["versicolor", str(counter1) + "(" + str("%.1f" % (counter1 * 100 / result)) + "%)"],
    ["virginica", str(counter2) + "(" + str("%.1f" % (counter2 * 100 / result)) + "%)"],
    ["Razem", str(result) + "(" + str(float(result / result) * 100) + "%)"]
    ]

    table = ax.table(cellText=data, loc='center')

    table.set_fontsize(14)
    table.scale(1,4)
    ax.axis('off')

    plt.show()


def min_traits(col1, col2, col3, col4):

    min1 = col1[0]
    min2 = col2[0]
    min3 = col3[0]
    min4 = col4[0]
    for var in col1:
        if var < min1:
            min1 = var

    for var in col2:
        if var < min2:
            min2 = var

    for var in col3:
        if var < min3:
            min3 = var

    for var in col4:
        if var < min4:
            min4 = var

    print("min:" + str(min1) + " " + str(min2) + " " + str(min3) + " " + str(min4))


def max_traits(col1, col2, col3, col4):
    max1 = col1[0]
    for var in col1:
        if var > max1:
            max1 = var
    max2 = col2[0]
    for var in col2:
        if var > max2:
            max2 = var
    max3 = col3[0]
    for var in col3:
        if var > max3:
            max3 = var
    max4 = col4[0]
    for var in col4:
        if var > max4:
            max4 = var
    print("max:" + str(max1) + " " + str(max2) + " " + str(max3) + " " + str(max4))


def average_traits(col1, col2, col3, col4, res):

    ave1 = 0.0
    for sum in col1:
        ave1 += sum
    ave2 = 0.0
    for sum in col2:
        ave2 += sum
    ave3 = 0.0
    for sum in col3:
        ave3 += sum
    ave4 = 0.0
    for sum in col4:
        ave4 += sum
    ave1 /= res
    ave2 /= res
    ave3 /= res
    ave4 /= res
    print("average:" + str(ave1) + " " + str(ave2) + " " + str(ave3) + " " + str(ave4))

    varx1 = 0
    varx2 = 0
    varx3 = 0
    varx4 = 0
    for var in range(len(col1)):
        varx1 += (col1[var] - ave1) ** 2
    for var in range(len(col2)):
        varx2 += (col2[var] - ave2) ** 2
    for var in range(len(col3)):
        varx3 += (col3[var] - ave3) ** 2
    for var in range(len(col4)):
        varx4 += (col4[var] - ave4) ** 2
    warian1 = varx1 / (res - 1)
    dev1 = warian1 ** 0.5
    warian2 = varx2 / (res - 1)
    dev2 = warian2 ** 0.5
    warian3 = varx3 / (res - 1)
    dev3 = warian3 ** 0.5
    warian4 = varx4 / (res - 1)
    dev4 = warian4 ** 0.5
    print("deviation:" + str(dev1) + " " + str(dev2) + " " + str(dev3) + " " + str(dev4))


def median_traits(col1, col2, col3, col4, result):

    median1 = 0
    median2 = 0
    median3 = 0
    median4 = 0
    n = result
    n2 = n / 2
    q11 = 0
    q31 = 0

    q12 = 0
    q32 = 0

    q13 = 0
    q33 = 0

    q14 = 0
    q34 = 0

    for i in range(n):
        swapped = False

        for j in range(0, n - i - 1):

            if col1[j] > col1[j + 1]:
                buf = col1[j]
                col1[j] = col1[j+1]
                col1[j+1] = buf
                swapped = True
        if not swapped:
            break

    for i in range(n):
        swapped = False

        for j in range(0, n - i - 1):

            if col2[j] > col2[j + 1]:
                buf = col2[j]
                col2[j] = col2[j+1]
                col2[j+1] = buf
                swapped = True
        if not swapped:
            break

    for i in range(n):
        swapped = False

        for j in range(0, n - i - 1):

            if col3[j] > col3[j + 1]:
                buf = col3[j]
                col3[j] = col3[j+1]
                col3[j+1] = buf
                swapped = True
        if not swapped:
            break

    for i in range(n):
        swapped = False

        for j in range(0, n - i - 1):

            if col4[j] > col4[j + 1]:
                buf = col4[j]
                col4[j] = col4[j+1]
                col4[j+1] = buf
                swapped = True
        if not swapped:
            break

    if n % 2 == 0:
        median1 = (col1[n2] + col1[n2 + 1]) / 2
        median2 = (col2[n2] + col2[n2 + 1]) / 2
        median3 = (col3[n2] + col3[n2 + 1]) / 2
        median4 = (col4[n2] + col4[n2 + 1]) / 2
    else:
        median1 = col1[n2 + 1]
        median2 = col2[n2 + 1]
        median3 = col3[n2 + 1]
        median4 = col4[n2 + 1]

    print("mediana:" + str(median1) + " " + str(median2) + " " + str(median3) + " " + str(median4))

    if n2 % 2 == 0:
        q11 = (col1[n2 / 2] + col1[(n2 + 1) / 2]) / 2
        q31 = (col1[n2 + (n2 / 2)] + col1[n2 + (n2 + 1) / 2]) / 2
        q12 = (col2[n2 / 2] + col2[(n2 + 1) / 2]) / 2
        q32 = (col2[n2 + (n2 / 2)] + col2[n2 + (n2 + 1) / 2]) / 2
        q13 = (col3[n2 / 2] + col3[(n2 + 1) / 2]) / 2
        q33 = (col3[n2 + (n2 / 2)] + col3[n2 + (n2 + 1) / 2]) / 2
        q14 = (col4[n2 / 2] + col4[(n2 + 1) / 2]) / 2
        q34 = (col4[n2 + (n2 / 2)] + col4[n2 + (n2 + 1) / 2]) / 2
    else:
        q11 = (col1[(n2 + 1) / 2])
        q31 = (col1[n2 + (n2 + 1) / 2])
        q12 = (col2[(n2 + 1) / 2])
        q32 = (col2[n2 + (n2 + 1) / 2])
        q13 = (col3[(n2 + 1) / 2])
        q33 = (col3[n2 + (n2 + 1) / 2])
        q14 = (col4[(n2 + 1) / 2])
        q34 = (col4[n2 + (n2 + 1) / 2])
        print("q1:" + str(q11) + " " + str(q12) + " " + str(q13) + " " + str(q14))
        print("q3:" + str(q31) + " " + str(q32) + " " + str(q33) + " " + str(q34))


def show_histogram(col1, col2, col3, col4):
    plt.rcParams.update({'font.size': 16})
    fig = plt.figure(figsize=(10, 7))
    plt.hist(col1, align='mid', edgecolor='black', range=(4.0, 8.0), bins=8)
    plt.title("Długość działki kielicha")
    plt.xlabel("Długość (cm)")
    plt.ylabel("Liczebność")

    fig = plt.figure(figsize=(10, 7))
    plt.hist(col2, align='mid', edgecolor='black', range=(2.0, 4.4), bins=12)
    plt.xticks(np.arange(min(col2) - 0.2, max(col2) + 0.2, 0.2))
    plt.title("Szerokość działki kielicha")
    plt.xlabel("Szerokość (cm)")
    plt.ylabel("Liczebność")

    fig = plt.figure(figsize=(10, 7))
    plt.hist(col3, align='mid', edgecolor='black', range=(1.0, 7.0), bins=12)
    plt.xticks(np.arange(min(col3) - 0.5, max(col3) + 0.5, 0.5))
    plt.title("Długość płatka")
    plt.xlabel("Długość (cm)")
    plt.ylabel("Liczebność")

    fig = plt.figure(figsize=(10, 7))
    plt.hist(col4, align='mid', edgecolor='black', range=(0.1, 2.5), bins=12)
    plt.xticks(np.arange(min(col4), max(col4) + 0.2, 0.2))
    plt.title("Szerokość płatka")
    plt.xlabel("Długość (cm)")
    plt.ylabel("Liczebność")

    plt.show()


def show_boxes(col1, col2, col3, col4, last):
    b11 = [None] * 50
    b12 = [None] * 50
    b13 = [None] * 50
    for i in range(len(last)):
        if last[i] == 0:
            b11[i] = col1[i]
        if last[i] == 1:
            b12[i % 50] = col1[i]
        if last[i] == 2:
            b13[i % 50] = col1[i]
    data1 = [b11, b12, b13]
    fig = plt.figure(figsize=(10, 7))
    plt.rcParams.update({'font.size': 16})
    plt.boxplot(data1)
    plt.ylabel("Długość (cm)")
    plt.xlabel("Gatunek")
    plt.title("Długość działki kielicha")
    plt.yticks(np.arange(min(col1) - 0.3, max(col1) + 0.2, 1))
    plt.xticks([1, 2, 3], ['setosa', 'versicolor', 'virginica'])

    b21 = [None] * 50
    b22 = [None] * 50
    b23 = [None] * 50
    for i in range(len(last)):
        if last[i] == 0:
            b21[i] = col2[i]
        if last[i] == 1:
            b22[i % 50] = col2[i]
        if last[i] == 2:
            b23[i % 50] = col2[i]
    data2 = [b21, b22, b23]
    fig = plt.figure(figsize=(10, 7))
    plt.boxplot(data2)
    plt.ylabel("Szerokość (cm)")
    plt.xlabel("Gatunek")
    plt.title("Szerokość działki kielicha")
    plt.yticks(np.arange(min(col2), max(col2), 1))
    plt.xticks([1, 2, 3], ['setosa', 'versicolor', 'virginica'])

    b31 = [None] * 50
    b32 = [None] * 50
    b33 = [None] * 50
    for i in range(len(last)):
        if last[i] == 0:
            b31[i] = col3[i]
        if last[i] == 1:
            b32[i % 50] = col3[i]
        if last[i] == 2:
            b33[i % 50] = col3[i]
    data3 = [b31, b32, b33]
    fig = plt.figure(figsize=(10, 7))
    plt.boxplot(data3)
    plt.ylabel("Długość (cm)")
    plt.xlabel("Gatunek")
    plt.title("Długość płatka")
    plt.xticks([1, 2, 3], ['setosa', 'versicolor', 'virginica'])

    b41 = [None] * 50
    b42 = [None] * 50
    b43 = [None] * 50
    for i in range(len(last)):
        if last[i] == 0:
            b41[i] = col4[i]
        if last[i] == 1:
            b42[i % 50] = col4[i]
        if last[i] == 2:
            b43[i % 50] = col4[i]
    data4 = [b41, b42, b43]
    fig = plt.figure(figsize=(10, 7))
    plt.boxplot(data4)
    plt.ylabel("Szerokość (cm)")
    plt.xlabel("Gatunek")
    plt.title("Szerokość płatka")
    plt.yticks(np.arange(min(col4) - 0.1, max(col4) - 0.1, 1))
    plt.xticks([1, 2, 3], ['setosa', 'versicolor', 'virginica'])
    plt.show()


def read_file():

    data = pd.read_csv("data.csv", header=None)
    last_columns = data.iloc[:, -1]
    first_column = data.iloc[:, 0]
    second_column = data.iloc[:, 1]
    third_column = data.iloc[:, 2]
    fourth_column = data.iloc[:, 3]
    counter0 = 0
    counter1 = 0
    counter2 = 0
    for column in last_columns:
        if column == 0:
            counter0 = counter0 + 1
        elif column == 1:
            counter1 = counter1 + 1
        elif column == 2:
            counter2 = counter2 + 1

    result = counter0 + counter1 + counter2
    percentage0 = (counter0 * 100) / result
    percentage1 = (counter1 * 100) / result
    percentage2 = (counter2 * 100) / result
    percentage4 = (result * 100 / result)
    print("Setosa: " + str(counter0) + " " + str(percentage0) + " % " + "\n" + "Versicolor: " + str(counter1)
          + " " + str(percentage1) + " % " + "\n" + "Virginica: " + str(counter2) + " " + str(percentage2) + " % "
          + "\n" + "Razem: " + str(counter0 + counter1 + counter2) + " " + str(percentage4) + " % ")


    #view_first_table(counter0, counter1, counter2, result)

    min_traits(first_column, second_column, third_column, fourth_column)

    max_traits(first_column, second_column, third_column, fourth_column)

    average_traits(first_column, second_column, third_column, fourth_column, result)

    median_traits(first_column, second_column, third_column, fourth_column, result)

    show_histogram(first_column, second_column, third_column, fourth_column)

    show_boxes(first_column, second_column, third_column, fourth_column, last_columns)


read_file()
