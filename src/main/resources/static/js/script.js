/**
 * WEBSITE: https://themefisher.com
 * TWITTER: https://twitter.com/themefisher
 * FACEBOOK: https://www.facebook.com/themefisher
 * GITHUB: https://github.com/themefisher/
 */

(function ($) {
  'use strict';

  // navbarDropdown
	if ($(window).width() < 992) {
		$('.navigation .dropdown-toggle').on('click', function () {
			$(this).siblings('.dropdown-menu').animate({
				height: 'toggle'
			}, 300);
		});
  }

  //  Count Up
  function counter() {
    var oTop;
    if ($('.counter').length !== 0) {
      oTop = $('.counter').offset().top - window.innerHeight;
    }
    if ($(window).scrollTop() > oTop) {
      $('.counter').each(function () {
        var $this = $(this),
          countTo = $this.attr('data-count');
        $({
          countNum: $this.text()
        }).animate({
          countNum: countTo
        }, {
          duration: 1000,
          easing: 'swing',
          step: function () {
            $this.text(Math.floor(this.countNum));
          },
          complete: function () {
            $this.text(this.countNum);
          }
        });
      });
    }
  }
  $(window).on('scroll', function () {
    counter();
		//.Scroll to top show/hide
    var scrollToTop = $('.scroll-top-to'),
      scroll = $(window).scrollTop();
    if (scroll >= 200) {
      scrollToTop.fadeIn(200);
    } else {
      scrollToTop.fadeOut(100);
    }
  });
	// scroll-to-top
  $('.scroll-top-to').on('click', function () {
    $('body,html').animate({
      scrollTop: 0
    }, 500);
    return false;
  });
    
  // -----------------------------
  //  Video Replace
  // -----------------------------
  $('.video-box').click(function () {
    var video = '<div class="embed-responsive embed-responsive-16by9 mb-4"><iframe class="embed-responsive-item" src="' + $(this).attr('data-video-url') + '" allowfullscreen></iframe></div>';
    $(this).parent('.video').replaceWith(video);
  });

  // niceSelect

  $('select:not(.ignore)').niceSelect();

  // blog post-slider
  $('.post-slider').slick({
    dots: false,
    arrows: false,
    slidesToShow: 1,
    slidesToScroll: 1,
    adaptiveHeight: true,
    autoplay: true,
    fade: true
  });

  // Client Slider 
  $('.category-slider').slick({
    dots: false,
    slidesToShow: 5,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 1500,
    nextArrow: '<i class="fa fa-chevron-right arrow-right"></i>',
    prevArrow: '<i class="fa fa-chevron-left arrow-left"></i>',
    responsive: [{
      breakpoint: 1024,
      settings: {
        slidesToShow: 4,
        slidesToScroll: 1,
        infinite: true,
        dots: false
      }
    },
    {
      breakpoint: 600,
      settings: {
        slidesToShow: 3,
        slidesToScroll: 1
      }
    },
    {
      breakpoint: 480,
      settings: {
        slidesToShow: 3,
        slidesToScroll: 1,
        arrows: false
      }
    }
    // You can unslick at a given breakpoint now by adding:
    // settings: "unslick"
    // instead of a settings object
    ]
  });

  // trending-ads-slide 

  $('.trending-ads-slide').slick({
    dots: false,
    arrows: false,
    slidesToShow: 3,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 800,
    responsive: [{
      breakpoint: 1024,
      settings: {
        slidesToShow: 2,
        slidesToScroll: 1,
        infinite: true,
        dots: false
      }
    },
    {
      breakpoint: 600,
      settings: {
        slidesToShow: 2,
        slidesToScroll: 1
      }
    },
    {
      breakpoint: 480,
      settings: {
        slidesToShow: 1,
        slidesToScroll: 1
      }
    }
    // You can unslick at a given breakpoint now by adding:
    // settings: "unslick"
    // instead of a settings object
    ]
  });


  // product-slider
  $('.product-slider').slick({
    dots: true,
    arrows: true,
    slidesToShow: 1,
    slidesToScroll: 1,
    autoplay: false,
    autoplaySpeed: false,
    nextArrow: '<i class="fa fa-chevron-right arrow-right"></i>',
    prevArrow: '<i class="fa fa-chevron-left arrow-left"></i>',
    customPaging: function (slider, i) {
      var image = $(slider.$slides[i]).data('image');
      return '<img class="img-fluid" src="' + image + '" alt="product-img">';
    }
  });



  // tooltip
  $(function () {
    $('[data-toggle="tooltip"]').tooltip();
  });

  // bootstrap slider range
  $('.range-track').slider({});
  $('.range-track').on('slide', function (slideEvt) {
    $('.value').text('$' + slideEvt.value[0] + ' - ' + '$' + slideEvt.value[1]);
  });


})(jQuery);

function togglePriceField() {
  var negotiable = document.getElementById("negotiableCheckbox");
  var priceField = document.getElementById("price");

  if (negotiable.checked) {
    priceField.style.display = "none";
    priceField.value = "";
  } else {
    priceField.style.display = "block";
  }
}

window.onload = function() {
  togglePriceField();
};

const stars = document.querySelectorAll('.star');
const averageRatingText = document.getElementById('average-rating').innerText.trim();

console.log('Textul mediu al ratingului:', averageRatingText);

// Înlocuiește virgula cu punct
const formattedRatingText = averageRatingText.replace(',', '.');
const match = formattedRatingText.match(/(\d+\.\d+)/);

if (match) {
  console.log('Potrivire găsită:', match[1]);
} else {
  console.log('Nicio potrivire găsită');
}

const averageRating = match ? parseFloat(match[1]) : 0; // Verifică dacă a găsit o potrivire

console.log('Ratingul mediu convertit:', averageRating);

function fillStarsBasedOnAverage(rating) {
  let totalFilledStars = 0;

  if (rating >= 4.5) {
    totalFilledStars = 5;
  } else if (rating >= 3.5) {
    totalFilledStars = 4;
  } else if (rating >= 2.5) {
    totalFilledStars = 3;
  } else if (rating >= 1.5) {
    totalFilledStars = 2;
  } else if (rating >= 0.5) {
    totalFilledStars = 1;
  }

  stars.forEach((star, index) => {
    if (index < totalFilledStars) {
      star.classList.add('filled');
    } else {
      star.classList.remove('filled');
    }
  });
}

fillStarsBasedOnAverage(averageRating);

stars.forEach((star, index) => {
  star.addEventListener('mouseover', () => {
    fillStars(index);
  });

  star.addEventListener('mouseout', () => {
    resetStars();
  });

  star.addEventListener('click', () => {
    const rating = index + 1;
    document.getElementById('rating-value').value = rating;
    document.getElementById('rating-form').submit();
  });
});

function fillStars(index) {
  stars.forEach((star, i) => {
    if (i <= index) {
      star.classList.add('filled');
    } else {
      star.classList.remove('filled');
    }
  });
}

function resetStars() {
  fillStarsBasedOnAverage(averageRating);
}