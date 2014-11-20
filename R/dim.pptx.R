#' @title Get layout information on a PowerPoint slide
#'
#' @description Returns slide width and height, position and dimension of the next available shape
#' in the current slide. 
#' 
#' @param x Object of class \code{pptx}
#' @examples
#' \donttest{
#' doc = pptx( title = "title" )
#' doc = addSlide( doc, "Title and Content" )
#' dim(doc)
#' }
#' @seealso \code{\link{pptx}}, \code{\link{dim.docx}}
#' @method dim pptx
#' @S3method dim pptx
dim.pptx = function( x ){
	if( !is.null(x$current_slide) )
		temp = .jcall(x$current_slide, "[I", "getShapeDimensions")
	else temp = rep(0.0, 4 )
	
	out = list( position = round( c( left = temp[1], top = temp[2] ) / 914400, 5 )
			, size = round( c(width = temp[3], height = temp[4]) / 914400, 5 )
			, slide.dim = round( c(width = .jcall(x$obj, "I", "getDocWidth")
				, height = .jcall(x$obj, "I", "getDocHeight") ) / 914400, 5 )
	)
	out
}
