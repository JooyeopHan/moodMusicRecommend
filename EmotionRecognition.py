import numpy as np
from luxand import luxand
import pprint

def initial():

    #luxand api 객체 생성
    client = luxand("ac2d5f607fd44077b690949b3893abfc")
    return client

# comtempt+disgust 하기로 했다.
def emotioning(file,client, emotions_position):
    #이미지로 emotion 값 도출
    pre_result = client.emotions(photo = file[0])
    after_result = client.emotions(photo = file[1])

    print(pre_result)
    print(after_result)

    #contempt 와 disgust 를 disgust로 통합 함수
    def disgusting(emotions):
        if 'contempt' in emotions.keys() :
            if 'disgust' in emotions.keys():
                emotions['disgust'] = emotions['contempt']+emotions['disgust']
                del emotions['contempt']
            else:
                emotions['disgust'] = emotions['contempt']
                del emotions['contempt']
        return emotions

    pre_emotions = disgusting(pre_result[0]['emotions'])
    after_emotions = disgusting(after_result[0]['emotions'])

    pre_e = sorted(pre_emotions.items(), key = lambda item: item[1],reverse=True)
    after_e = sorted(after_emotions.items(),key = lambda item: item[1], reverse=True)

    print(pre_e)
    print(after_e)
    #내분점 도출 함수
    def pointing(emotion):
        result = []
        for n,(k,v) in enumerate(emotion.items()):
            if n == 0:
                result = emotions_position[k]
                b = v
            else:
                a_pos = result
                b_pos = emotions_position[k]
                a = v
                ab = a+b
                mx = float(a_pos[0])*b+float(b_pos[0])*a
                my = float(a_pos[1])*b+float(b_pos[1])*a
                result = [mx/ab,my/ab]
        return result


    #내분점 도출
    pre_e_point = pointing(pre_emotions)
    after_e_point = pointing(after_emotions)
    pre_e_point = np.array(pre_e_point)
    after_e_point = np.array(after_e_point)

    return after_e_point-pre_e_point,pre_e[0][0],after_e[0][0]