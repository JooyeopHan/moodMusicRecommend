import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import numpy as np
from sklearn.preprocessing import MaxAbsScaler,MinMaxScaler, StandardScaler

def readData():
    pd.set_option('display.max_columns', None)
    pd.set_option('display.max_rows', None)

    muse = pd.read_csv('muse_v3.csv', index_col=0, encoding='utf-8')

    # print(muse.columns)
    # print(muse.head())
    # print(muse.seeds.head())
    # print(muse.seeds.dtypes)
    # # print(muse["seeds"].value_counts())
    # print(muse.seeds.astype(str).head())
    # print(muse['number_of_emotion_tags'].value_counts())


    muse['emotion1'] = list(map(lambda data : data[1:-1].split(",")[0][1:-1], muse['seeds']))
    # muse['emotion2'] = list(map(lambda data : data.iloc[:,0][1:-1].split(",")[1] if data.iloc[:,1] >= 2 else None, muse[['seeds','number_of_emotion_tags']]))

    # print(muse.emotion1.head(10))
    return muse


def process(muse):

    def emoMapping(muse):
        angry_list = ['aggressive', 'angry','intense', 'harsh', 'fierce','bitter']
        happy_list = ['fun', 'cheerful', 'positive','bright','happy','delicate','light','humorous']
        sad_list = ['dark','gloomy','lonely','melancholy','bittersweet','sentimental']
        disgust_list = ['eerie']
        fear_list = ['scary','scared']
        contempt_list = ['messy']
        neutral_list=['calm', 'lyrical','relaxed']
        surprised_list=['intense']

        unknown_list = ['gentle','epic','dreamy','organic','romantic','erotic','uplifting','lush','mysterious','lazy','cold','exotic','gritty','sexy','serious','sleezy', 'intimate','martial','soothing'
                        ,'thoughtful','technical','sleazy','spiritual','dramatic','nocturnal','warm','meditative','sacred','witty','whimsical','ominous','mystical','poignant','tender','eccentric','nostalgic'
                        ,'silly','powerful']


        # muse.loc[muse['emotion1'].isin(angry_list),'emotion1'] = 'angry'
        # muse.loc[muse['emotion1'].isin(happy_list),'emotion1'] = 'happy'
        # muse.loc[muse['emotion1'].isin(sad_list),'emotion1'] = 'sad'
        muse.loc[muse['emotion1'].isin(contempt_list),'emotion1'] = 'contempt'
        muse.loc[muse['emotion1'].isin(neutral_list),'emotion1'] = 'neutral'
        muse.loc[muse['emotion1'].isin(disgust_list),'emotion1'] = 'disgust'
        muse.loc[muse['emotion1'].isin(surprised_list),'emotion1'] = 'surprised'
        muse.loc[muse['emotion1'].isin(fear_list),'emotion1'] = 'fear'
        muse.drop(muse.loc[muse['emotion1'].isin(unknown_list)].index, inplace = True)



        # print(muse["emotion1"].value_counts().head(20))
        # print(muse["emotion1"].value_counts().count())
    def removeOutlier(muse):
        # muse.boxplot(column=['valence_tags', 'arousal_tags'])
        Q1 = muse[['valence_tags', 'arousal_tags']].quantile(q=0.25)
        Q3 = muse[['valence_tags', 'arousal_tags']].quantile(q=0.75)
        iqr = Q3['arousal_tags'] - Q1['arousal_tags']


        # condition= (muse['valence_tags']>Q3['valence_tags']+1.5*iqr)
        condition = muse['arousal_tags'] < Q1['arousal_tags']-1.5*iqr
        idx = muse[condition].index
        muse.drop(idx, inplace=True)

    emoMapping(muse)
    removeOutlier(muse)

# VA 평면 도식 (평균값)
def plotVA(muse):
    # 데이터 표준화
    transformer = StandardScaler()

    muse["valence_scaled"] = transformer.fit_transform(muse[["valence_tags"]])
    muse["arousal_scaled"] = transformer.fit_transform(muse[["arousal_tags"]])
    x1 = muse.loc[muse["emotion1"]=='angry']["valence_scaled"].mean()
    y1 =  muse.loc[muse["emotion1"]=='angry']["arousal_scaled"].mean()

    x2 = muse.loc[muse["emotion1"] == 'happy']["valence_scaled"].mean()
    y2 = muse.loc[muse["emotion1"] == 'happy']["arousal_scaled"].mean()

    x3 = muse.loc[muse["emotion1"] == 'sad']["valence_scaled"].mean()
    y3 = muse.loc[muse["emotion1"] == 'sad']["arousal_scaled"].mean()

    x4 = muse.loc[muse["emotion1"] == 'surprised']["valence_scaled"].mean()
    y4 = muse.loc[muse["emotion1"] == 'surprised']["arousal_scaled"].mean()

    x5 = muse.loc[muse["emotion1"] == 'contempt']["valence_scaled"].mean()
    y5 = muse.loc[muse["emotion1"] == 'contempt']["arousal_scaled"].mean()

    x6 = muse.loc[muse["emotion1"] == 'fear']["valence_scaled"].mean()
    y6 = muse.loc[muse["emotion1"] == 'fear']["arousal_scaled"].mean()


    plt.xlabel('Valence')
    plt.ylabel('Arousal')
    plt.xlim([-1.3, 1.3])  # X축의 범위: [xmin, xmax]
    plt.ylim([-1.3, 1.3])
    plt.scatter(x1, y1, s=200,color='red', alpha=0.5, label='angry')
    plt.scatter(x2, y2, s=200,color='green', alpha=0.5, label='happy')
    plt.scatter(x3, y3, s=200,color='blue', alpha=0.5, label='sad')
    plt.scatter(x4, y4, s=200,color='orange', alpha=0.5, label='surprised')
    plt.scatter(x5, y5, s=200,color='yellow', alpha=0.5, label='contempt')
    plt.scatter(x6, y6, s=200,color='black', alpha=0.5, label='fear')
    plt.scatter(0, 0, s=200,color='pink', alpha=0.5, label='neutral')
    plt.axvline(x=0, color='k',linestyle='--', linewidth = 1)  # draw x =0 axes
    plt.axhline(y=0, color='k',linestyle='--', linewidth = 1)

    plt.legend()
    plt.show()

# 음악 전체 산점도 도식 (Valence, Arousal)
def plot(muse):

    transformer = StandardScaler()
    muse["valence_scaled"] = transformer.fit_transform(muse[["valence_tags"]])
    muse["arousal_scaled"] = transformer.fit_transform(muse[["arousal_tags"]])
    x1 = muse.loc[muse["emotion1"]=='angry']["valence_scaled"]
    y1 =  muse.loc[muse["emotion1"]=='angry']["arousal_scaled"]

    x2 = muse.loc[muse["emotion1"] == 'happy']["valence_scaled"]
    y2 = muse.loc[muse["emotion1"] == 'happy']["arousal_scaled"]

    x3 = muse.loc[muse["emotion1"] == 'sad']["valence_scaled"]
    y3 = muse.loc[muse["emotion1"] == 'sad']["arousal_scaled"]

    # x4 = muse.loc[muse["emotion1"] == 'surprised']["valence_scaled"]
    # y4 = muse.loc[muse["emotion1"] == 'surprised']["arousal_scaled"]

    x5 = muse.loc[muse["emotion1"] == 'contempt']["valence_scaled"]
    y5 = muse.loc[muse["emotion1"] == 'contempt']["arousal_scaled"]

    # x6 = muse.loc[muse["emotion1"] == 'fear']["valence_scaled"]
    # y6 = muse.loc[muse["emotion1"] == 'fear']["arousal_scaled"]

    x7 = muse.loc[muse["emotion1"] == 'neutral']["valence_scaled"]
    y7 = muse.loc[muse["emotion1"] == 'neutral']["arousal_scaled"]
    plt.xlabel('Valence')
    plt.ylabel('Arousal')
    plt.xlim([-1.5, 1.5])  # X축의 범위: [xmin, xmax]
    plt.ylim([-1.5, 1.5])
    plt.scatter(x1, y1, s=5,color='red', alpha=0.5, label='angry')
    plt.scatter(x2, y2, s=5,color='green', alpha=0.5, label='happy')
    plt.scatter(x3, y3, s=5,color='blue', alpha=0.5, label='sad')
    # plt.scatter(x4, y4, s=5,color='orange', alpha=0.5, label='surprised')
    # plt.scatter(x5, y5, s=5,color='black', alpha=0.5, label='contempt')
    # plt.scatter(x6, y6, s=5,color='black', alpha=0.5, label='fear')
    # plt.scatter(x7, y7, s=5,color='pink', alpha=0.5, label='neutral')
    plt.axvline(x=0, color='k',linestyle='--', linewidth = 1)  # draw x =0 axes
    plt.axhline(y=0, color='k',linestyle='--', linewidth = 1)

    plt.legend()
    plt.show()

def VAPlane(muse):
    # 데이터 표준화
    transformer = StandardScaler()

    muse["valence_scaled"] = transformer.fit_transform(muse[["valence_tags"]])
    muse["arousal_scaled"] = transformer.fit_transform(muse[["arousal_tags"]])
    angry_x = muse.loc[muse["emotion1"]=='angry']["valence_scaled"].mean()
    angry_y =  muse.loc[muse["emotion1"]=='angry']["arousal_scaled"].mean()

    happy_x = muse.loc[muse["emotion1"] == 'happy']["valence_scaled"].mean()
    happy_y = muse.loc[muse["emotion1"] == 'happy']["arousal_scaled"].mean()

    sad_x = muse.loc[muse["emotion1"] == 'sad']["valence_scaled"].mean()
    sad_y = muse.loc[muse["emotion1"] == 'sad']["arousal_scaled"].mean()

    surprised_x = muse.loc[muse["emotion1"] == 'surprised']["valence_scaled"].mean()
    surprised_y = muse.loc[muse["emotion1"] == 'surprised']["arousal_scaled"].mean()

    contempt_x = muse.loc[muse["emotion1"] == 'contempt']["valence_scaled"].mean()
    contempt_y = muse.loc[muse["emotion1"] == 'contempt']["arousal_scaled"].mean()

    fear_x = muse.loc[muse["emotion1"] == 'fear']["valence_scaled"].mean()
    fear_y = muse.loc[muse["emotion1"] == 'fear']["arousal_scaled"].mean()
    return {"anger":[angry_x,angry_y],'happiness':[happy_x,happy_y],'sadness':[sad_x,sad_y],'surprise':[surprised_x,surprised_y],'disgust':[contempt_x,contempt_y],'fear':[fear_x,fear_y],"neutral":[0,0]}