# Food Recipe
> A JAVA application that allow users to explore new and delicious food recipes



<img src="FR Asset/Breakfast.png" width="300" height="600" ><img src="FR Asset/Desserts.png" width="300" height="600" ><img src="FR Asset/Search Description.png" width="300" height="600" ><img src="FR Asset/Searching.png" width="300" height="600" >



## Table of Contents

- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Acknowldegements](#acknowledgements)
- [Technology](#technology)
- [Some Useful Links](#some-useful-links)


## Prerequisites

- Download the repository
- Download Android Studio
- Android Device. Or install Virtual one with Target: Android 11.0 API 30

## Installation

```sh
$ git clone https://github.com/kabhamo/Food-Recipe-Application.git
```

## Acknowledgements
$
<br>
The Application interact with a REST API from <a href="https://forkify-api.herokuapp.com/" target="_blank" rel="nofollow">Forkify API</a>.
<br>
<br>The app will retrieve information from the website and display it in various view types.<br>
<br>I love food, I believe I am a good cooker so I made this application so it can help me to keep updated with the many delicious recipes we all love.<br>
I use this [Technology](#technology)
<br>to make it work and easy to use. I am so happy to share it with everyone<br>
<br>See the list of all available queries <a href="https://forkify-api.herokuapp.com/phrases.html" target="_blank" rel="nofollow">Search Queries</a>. <br>

<strong>Architecture Diagram</strong>
<br><br>
<div class="text-center">
<img class="img-fluid text-center" src="https://codingwithmitch.s3.amazonaws.com/static/blog/8/mvvm_architecture.png"/>
</div>
<br><br>

## Technology
<br>
	<ol>
		<li><strong>MVVM Architecture</strong>: ViewModel, Repository, Client structure</li>
		<li>REST API <strong>Retrofit2</strong></li>
		<li>Data Base Cache</li>
		<li>Singletons</li>
		<li><strong>RecyclerView Pagination</strong></li>
		<li>Observables, LiveData, MutableLiveData and MediatorLiveData</li>
		<li>Displaying Images using Glide</li>
		<li>SearchViews</li>
		<li>Async Task, ThreadPools, OOP</li>
		<li>Network Security Config for HTTP (API 28+)</li>
	</ol>


   <h3>Dependencies I Used: </h3>
   
		
		dependencies {
    def retrofitVersion = '2.4.0'
    def lifecycle_version = "1.1.1"
    def supportVersion = "28.0.0"
    def glideVersion = "4.12.0"

    implementation 'androidx.appcompat:appcompat:1.3.1'
    implementation 'com.google.android.material:material:1.4.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.1'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'

    //retrofit
    //noinspection GradleDependency
    implementation "com.squareup.retrofit2:retrofit:$retrofitVersion"

    // Retrofit gson converter
    //noinspection GradleDependency
    implementation "com.squareup.retrofit2:converter-gson:$retrofitVersion"

    // ViewModel and LiveData
    implementation "android.arch.lifecycle:extensions:$lifecycle_version"

    // CardViews
    implementation "androidx.cardview:cardview:1.0.0"

    // RecyclerViews
    implementation "androidx.recyclerview:recyclerview:1.2.1"
    // For control over item selection of both touch and mouse driven selection
    implementation "androidx.recyclerview:recyclerview-selection:1.1.0"

    // Design Support
    implementation "com.android.support:design:$supportVersion"

    // Glide
    //noinspection GradleDependency
    implementation "com.github.bumptech.glide:glide:$glideVersion"
    annotationProcessor "com.github.bumptech.glide:compiler:$glideVersion"

    // Circle ImageView
    implementation 'de.hdodenhof:circleimageview:3.1.0'

	}
	
<br>
	
### Some Useful links

- **POSTMAN:** https://www.postman.com/
- **MVVM:** https://www.tutorialspoint.com/mvvm/index.htm
- **Retrofit:** https://square.github.io/retrofit/
- **RecyclerView:** https://blog.mindorks.com/how-does-recyclerview-work-internally
- **Android Developer:** https://developer.android.com/
