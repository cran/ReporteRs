\name{as.html.RScript}
\alias{as.html.RScript}
\title{get HTML code from a RScript object}
\usage{
\method{as.html}{RScript}(object, ...)
}
\arguments{
  \item{object}{the \code{RScript} object}

  \item{...}{further arguments passed to other methods -
  not used.}
}
\value{
a character value
}
\description{
get HTML code from a RScript object
}
\examples{
\donttest{
my_rscript = RScript( text = "ls()" )
as.html( my_rscript )
}
}
\seealso{
\code{\link{RScript}}
}

