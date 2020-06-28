
<img src="https://user-images.githubusercontent.com/53253298/85924344-b6ec4c80-b8cc-11ea-974c-e998f89c6cc0.png" alt="final-architecture" style="zoom: 67%;" />

## MVVM

**Model - View - ViewModel** 

1. Activity와 Fragmnet는 UI만 그릴 뿐, 

   필요한 데이터들을 LiveData로  ViewModel 에서 제공해준다.

- LiveData는 수명주기를 인식해서 앱의 수명주기를 수동으로 처리해 주지 않아도 된다

  (savedPrepareInstance 같은걸 안해줘도 된다.)
