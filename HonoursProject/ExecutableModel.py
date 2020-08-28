import pandas as pd
import tensorflow as tf
import sys
from keras.engine.saving import load_model
from keras.models import model_from_json


# A utility method to create a tf.data dataset from a Pandas Dataframe
def df_to_dataset(dataframe, shuffle=True, batch_size=32):
  dataframe = dataframe.copy()
  labels = dataframe.pop('IsCanceled')
  ds = tf.data.Dataset.from_tensor_slices((dict(dataframe), labels))
  if shuffle:
    ds = ds.shuffle(buffer_size=len(dataframe))
  ds = ds.batch(batch_size)
  return ds


predict_df = pd.read_csv('E:\\stech\\Documents\\Uni\\4thYear\\Honours\\Data\\H1Clean.csv')

#change null values to -1
predict_df[['Agent', 'Company']] = predict_df[['Agent', 'Company']].fillna(-1)


pset = df_to_dataset(predict_df, shuffle=False, batch_size=32)


#model = load_model('C:\\Users\\stech\\git\\HonoursProject\\UIProject\\bin\\businessLayer\\model.h5')

# load json and create model
json_file = open('C:\\Users\\stech\\git\\HonoursProject\\UIProject\\bin\\businessLayer\\model.json', 'r')
loaded_model_json = json_file.read()
json_file.close()
loaded_model = model_from_json(loaded_model_json)
# load weights into new model
loaded_model.load_weights("C:\\Users\\stech\\git\\HonoursProject\\UIProject\\bin\\businessLayer\\model.h5")
print("Loaded model from disk")

loaded_model.compile(optimizer='adam',
        loss='binary_crossentropy',
        metrics=['accuracy'])

print(loaded_model.predict_proba(pset, batch_size=None))


if __name__ == '__main__':
    pass