'''
Created on 6 Feb 2020

@author: stech
'''

import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
import tensorflow as tf
from tensorflow import feature_column
from tensorflow.keras import layers
import matplotlib.pyplot as plt
from numpy import array
from numpy import mean

# A utility method to create a tf.data dataset from a Pandas Dataframe
def df_to_dataset(dataframe, shuffle=True, batch_size=32):
  dataframe = dataframe.copy()
  labels = dataframe.pop('IsCanceled')
  ds = tf.data.Dataset.from_tensor_slices((dict(dataframe), labels))
  if shuffle:
    ds = ds.shuffle(buffer_size=len(dataframe))
  ds = ds.batch(batch_size)
  return ds





tf.keras.backend.clear_session()

#read in csv
data = pd.read_csv('E:/stech/Documents/Uni/4thYear/Honours/Data/H2 Clean.csv')

#print(data)

#######READ IN PREDICTION CODE##########
pdf = pd.read_csv('E:/stech/Documents/Uni/4thYear/Honours/Data/H1Clean.csv')
pd.set_option('max_columns', None)

#change null values to -1
pdf[['Agent', 'Company']] = pdf[['Agent', 'Company']].fillna(-1)


pset = df_to_dataset(pdf, shuffle=False, batch_size=32)

#########PREDICTION CODE ENDS############

#change null values to -1
data[['Agent', 'Company']] = data[['Agent', 'Company']].fillna(-1)


train, test = train_test_split(data, test_size=0.2)
train, val = train_test_split(train, test_size=0.2)
print(len(train), 'train examples')
print(len(val), 'validation examples')
print(len(test), 'test examples')

batch_size = 32
train_ds = df_to_dataset(train, batch_size=batch_size)
val_ds = df_to_dataset(val, shuffle=False, batch_size=batch_size)
test_ds = df_to_dataset(test, shuffle=False, batch_size=batch_size)
  


feature_columns = []


meal = feature_column.categorical_column_with_vocabulary_list(
      'Meal', ['BB', 'FB', 'HB', 'SC'])

meal_one_hot = feature_column.indicator_column(meal)

feature_columns.append(meal_one_hot)


marketSegment = feature_column.categorical_column_with_vocabulary_list(
      'MarketSegment', ['Complementary', 'Corporate', 'Direct', 'Groups', 'Offline TA/TO', 'Online TA'])

marketSegment_one_hot = feature_column.indicator_column(marketSegment)

feature_columns.append(marketSegment_one_hot)


disChannel = feature_column.categorical_column_with_vocabulary_list(
      'DistributionChannel', ['Corporate', 'Direct', 'TA/TO', 'Undefined'])

disChannel_one_hot = feature_column.indicator_column(disChannel)

feature_columns.append(disChannel_one_hot)


resRoomType = feature_column.categorical_column_with_vocabulary_list(
      'ReservedRoomType', ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'L'])

resRoomType_one_hot = feature_column.indicator_column(resRoomType)

feature_columns.append(resRoomType_one_hot)


asRoomType = feature_column.categorical_column_with_vocabulary_list(
      'AssignedRoomType', ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'L'])

asRoomType_one_hot = feature_column.indicator_column(asRoomType)

feature_columns.append(asRoomType_one_hot)


depositType = feature_column.categorical_column_with_vocabulary_list(
      'DepositType', ['No Deposit', 'Non Refund', 'Refundable'])

depositType_one_hot = feature_column.indicator_column(depositType)

feature_columns.append(depositType_one_hot)


custType = feature_column.categorical_column_with_vocabulary_list(
      'CustomerType', ['Contract', 'Group', 'Transient', 'Transient-Party'])

custType_one_hot = feature_column.indicator_column(custType)

feature_columns.append(custType_one_hot)


resStatus = feature_column.categorical_column_with_vocabulary_list(
      'ReservationStatus', ['Canceled', 'Check-Out', 'No Show'])

resStatus_one_hot = feature_column.indicator_column(resStatus)

feature_columns.append(resStatus_one_hot)


arrivalMonth = feature_column.categorical_column_with_vocabulary_list(
      'ArrivalDateMonth', ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'])

arrivalMonth_one_hot = feature_column.indicator_column(arrivalMonth)

feature_columns.append(arrivalMonth_one_hot)


countryNames = feature_column.categorical_column_with_vocabulary_file(
    'Country', 'E:/stech/Documents/Uni/4thYear/Honours/CountryNames.csv', vocabulary_size=None, dtype=tf.dtypes.string,
    default_value=None, num_oov_buckets=0
)

countryNames_one_hot = feature_column.indicator_column(countryNames)

feature_columns.append(countryNames_one_hot)


for header in ['LeadTime', 'ArrivalDateWeekNumber', 'StaysInWeekendNights',
                       'StaysInWeekNights', 'Adults', 'Children', 'Babies', 'PreviousCancellations', 'PreviousBookingsNotCanceled', 
                       'BookingChanges', 'DaysInWaitingList', 'RequiredCarParkingSpaces', 'TotalOfSpecialRequests', 'ADR', 'ArrivalDateDayOfMonth', 'ArrivalDateYear',
                       'Agent', 'Company']:
  feature_columns.append(feature_column.numeric_column(header))


feature_layer = tf.keras.layers.DenseFeatures(feature_columns)



avg_acc_list = list()
avg_loss_list = list()

for i in range(1):

    model = tf.keras.Sequential([
    feature_layer,
  layers.Dense(16, input_dim=14, activation='relu'),
  layers.Dense(16, activation='relu'),
  layers.Dense(1, activation='sigmoid')
  ])

    model.compile(optimizer='adam',
        loss='binary_crossentropy',
        metrics=['accuracy'])

    history = model.fit(train_ds,
        validation_data=val_ds,
        epochs=10, verbose=0)
    
    avg_acc_list.append(history.history['val_accuracy'][-1])
    avg_loss_list.append(history.history['val_loss'][-1])

plt.plot(avg_acc_list)    
plt.title('Model accuracy')
plt.ylabel('Validation Accuracy')
plt.xlabel('Iteration')

plt.show()

plt.plot(avg_loss_list)    
plt.title('Model loss')
plt.ylabel('Validation Loss')
plt.xlabel('Iteration')

plt.show()

print(model.predict_proba(pset, batch_size=None))


if __name__ == '__main__':
    pass