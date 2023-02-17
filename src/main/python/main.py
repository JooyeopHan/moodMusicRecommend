from flask import Flask,request,render_template
import EmotionRecognition
import Spotify.spotify
import sortingInterface
from DataAnaylsis import vaplane

def detecting():
    #이미지 파일 받고 저장
    f1 = request.files["file1"]
    f2 = request.files["file2"]
    f1.save(f1.filename)
    f2.save(f2.filename)
    print("hello.py")
    #API 사용
    file = [f1.filename,f2.filename]
    client = EmotionRecognition.initial()
    emotions_position = vaplane.MakePlane()
    # 전/ 후 사진으로 추출된 벡터
    result = EmotionRecognition.emotioning(file,client,emotions_position)
    # print(result)
    sp = Spotify.spotify.initial()
    pre_tracklist = Spotify.spotify.spotifing(sp)
    # print(pre_tracklist, type(pre_tracklist))
    # 랜덤 추출된 50개의 곡 dataframe, column = ['artist_name','track_name','energy', 'valence']
    # print(pre_tracklist)
    result = sortingInterface.sorting(pre_tracklist,result[0])

    #result = 10개의 중복되지 않은 [[artist,name],...]
    return result


