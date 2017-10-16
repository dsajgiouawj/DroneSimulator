[![Build Status](https://travis-ci.org/dsajgiouawj/DroneSimulator.svg?branch=master)](https://travis-ci.org/dsajgiouawj/DroneSimulator)
[![Coverage Status](https://coveralls.io/repos/github/dsajgiouawj/DroneSimulator/badge.svg?branch=master)](https://coveralls.io/github/dsajgiouawj/DroneSimulator?branch=master)
# DroneSimulator
## 必要環境
最新版のJava8をインストールしてください
## GUIでの使い方
jarをダブルクリックすると起動します  
適当なパラメーターを入力してstartを押すと実行します  
青い点が被災者、基本的にはドローンは白い丸です(状況によって変わることもあります)  
ドローンの丸の大きさはカメラの視野と等しいです
速度変更、拡大縮小は表示タブからできます
## CUIでの使い方
jarにクラスパスを通してjp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.CUIMainを実行してください  
Windowsの場合はコマンドプロンプトを開きjarと同じフォルダに移動して以下のコマンドを打ってください(jarファイル名はダウンロードしたjarの名前)  
java -classpath jarファイル名 jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.CUIMain  
入力内容を指示されるので適切にパラメーターを入力してください  
## データの検証
dataフォルダの中にある*.inファイルの内容をCUIでの使い方に従って入力してください  
リダイレクト等を使うといいです