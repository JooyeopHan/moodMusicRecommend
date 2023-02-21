import pprint

from flask import Flask,request
import EmotionRecognition
import Spotify.spotify
import sortingInterface
from DataAnaylsis import vaplane
import base64


app = Flask(__name__)

@app.post("/detecting")
def detecting():
    #이미지 파일 받고 저장
    before_data = request.form['file1']
    after_data = request.form['file2']
    va = eval(request.form['member'])
    plane = {'anger': [va['angerV'],va['angerA']],'happiness': [va['happinessV'],va['happinessA']],'sadness': [va['sadnessV'],va['sadnessA']],'surprise': [va['surpriseV'],va['surpriseA']],'disgust': [va['disgustV'],va['disgustA']],'fear': [va['fearV'],va['fearA']],'neutral': [va['neutralV'],va['neutralA']]}
    pprint.pprint(plane)
    #va = request.args.get('vaplane')
    # before_data = io.BytesIO(before_data)
    # before_data2 = base64.b64decode(before_data)
    print(1)
    # print(before_data2)
    # e = open('result.png', 'wb')

    # e.write(before_data2)

    # e.close()
    #API 사용

    file = [before_data,after_data]
    # print(file)


    client = EmotionRecognition.initial()
    # emotions_position = vaplane.MakePlane()
    # print(emotions_position)


    # # 전/ 후 사진으로 추출된 벡터
    result = EmotionRecognition.emotioning(file,client,plane)
    # print(result)
    sp = Spotify.spotify.initial()
    pre_tracklist = Spotify.spotify.spotifing(sp)


    # print(pre_tracklist, type(pre_tracklist))
    # # 랜덤 추출된 50개의 곡 dataframe, column = ['artist_name','track_name','energy', 'valence']
    # print(pre_tracklist)


    resulted = sortingInterface.sorting(pre_tracklist,result[0])
    end = {'list': resulted, 'result':[result[1],result[2]]}
    #result = 10개의 중복되지 않은 [[artist,name],...]
    return end

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=9000)