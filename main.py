
from flask import Flask,request
import EmotionRecognition
import Spotify.spotify
import sortingInterface
from DataAnaylsis import vaplane
import pprint


app = Flask(__name__)

@app.post("/detecting")
def detecting():
    #이미지 파일 받고 저장
    before_data = request.form['file1']
    after_data = request.form['file2']
    # before_data = io.BytesIO(before_data)
    # before_data2 = base64.b64decode(before_data)
    print(1)
    #API 사용
    file = [before_data,after_data]
    client = EmotionRecognition.initial()
    emotions_position = vaplane.MakePlane()
    # # 전/ 후 사진으로 추출된 벡터
    result = EmotionRecognition.emotioning(file,client,emotions_position)
    # print(result)
    sp = Spotify.spotify.initial()
    pre_tracklist = Spotify.spotify.spotifing(sp)
    # print(pre_tracklist, type(pre_tracklist))
    # # 랜덤 추출된 50개의 곡 dataframe, column = ['artist_name','track_name','energy', 'valence']
    # print(pre_tracklist)
    result = sortingInterface.sorting(pre_tracklist,result[0])
    print(result)
    #result = 10개의 중복되지 않은 [[artist,name],...]
    return result

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=9000)