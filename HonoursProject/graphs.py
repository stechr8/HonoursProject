'''
Created on 7 Apr 2020

@author: stech
'''

import matplotlib.pyplot as plt

neuronsList = [8,16,32,64]
acc_list = [0.6596097, 0.70419705, 0.9979845, 0.7250632]

plt.plot(neuronsList, acc_list)    
plt.title('Hyperparameter\'s Effect (Batch Size) on Model Accuracy')
plt.ylabel('Mean Model Accuracy')
plt.xlabel('Batch Size')

plt.show()


if __name__ == '__main__':
    pass