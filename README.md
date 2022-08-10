## 구조

- MVVM 모델에 기반하여 kotlin-Flow 를 활용하여 데이터의 흐름을 제어하고 있습니다.
- 확장성을 고려한 Repository 구조를 취함으로써 데이터 증가에 따라 데이터 소스를 쉽게 추가할 수 있게 하였습니다.

## 네트워크

- Retrofit 과 함께 CallAdapter 를 Customizing 하여 사용했습니다.
- 결과값에 따라 성공 및 그 외 상태들로 구분하여 관리하고 이에 근거한 예외처리를 구현 하거나 구현할 수 있는 구조를 만들었습니다.
- paging 라이브러리 대신 직접 페이징을 구현함으로써 PagingLibrary 의 단점들을 보완할 수 있게 하였습니다.

## 사용기술

- AAC : ViewModel, DataBinding, BindingAdapter, NavigationComponent
- DI : Hilt
- Network : Retrofit, Moshi
- Async : Kotlin-Coroutine, Kotlin-Flow
- ect : Room, Glide

## 아쉬운점 & 개선할 사항

- ActivityViewModel 집중되어 있는 데이터를 각각에 ViewModel 로 분산하여 책임을 분리 예정
- 다양한 예외 상황에 대해서 테스트 코드를 작성해보고 이에따라 예외처리에 대한 정밀도를 높일 예정
- 코틀린 문법과 함수화를 통해 코드 간소할 예정
- 디테일한 UI 개선할 예정
