import spotipy
from spotipy.oauth2 import SpotifyClientCredentials
import pandas as pd
import pprint
pd.set_option('display.max_columns', None)
pd.set_option('display.max_rows', None)


def initial():
    cid = '51aa4312a72742918973df122dbd6ec2'
    secret = '44784889fb534057887a3660f796c13a'
    client_credentials_manager = SpotifyClientCredentials(client_id=cid, client_secret=secret)
    sp = spotipy.Spotify(client_credentials_manager=client_credentials_manager)

    return sp

def spotifing(sp):
    artist_name = []
    track_name = []
    image_url = []
    music_preview = []
    popularity = []
    track_id = []
    track = []

    for i in range(0,20,1):
        track_results = sp.search(q='year:2022', type='track',offset=i*50, limit=50, market='KR')
        track.append(track_results)


    for track_results in track:
        for t in track_results['tracks']['items']:
            artist_name.append(t['artists'][0]['name'])
            music_preview.append(t['preview_url'])
            image_url.append(t['album']['images'][0]['url'])
            track_name.append(t['name'])
            track_id.append(t['id'])
            popularity.append(t['popularity'])

    track_dataframe = pd.DataFrame({'artist_name' : artist_name, 'track_name' : track_name, 'track_id' : track_id, 'popularity' : popularity,"music_preview":music_preview,'image_url':image_url})

    track_features = []

    for t_id in track_dataframe['track_id']:
        af = sp.audio_features(t_id)
        track_features.append(af)
    # track_dataframe = pd.DataFrame(columns = ['danceability', 'energy', 'key', 'loudness', 'mode', 'speechiness', 'acousticness', 'instrumentalness', 'liveness', 'valence', 'tempo', 'type', 'id', 'url', 'track_href', 'analysis_url', 'duration_ms', 'time_signature'])
    feature_dataframe = pd.DataFrame(columns = ['energy',  'valence'])
    for item in track_features:
        for feat in item:
            feature_dataframe = feature_dataframe.append(feat, ignore_index=True)
    result = pd.concat([track_dataframe,feature_dataframe], axis = 1)
    return result.loc[:,['artist_name','track_name','music_preview','image_url','energy', 'valence']]

# sp = initial()
# print(spotifing(sp))
# a = sp.recommendation_genre_seeds()
# pprint.pprint(a)