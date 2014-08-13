\name{print.pptx}
\alias{print.pptx}
\title{print informations about an object of class \code{\link{pptx}}.}
\usage{
\method{print}{pptx}(x, ...)
}
\arguments{
  \item{x}{an object of class \code{\link{pptx}}}

  \item{...}{further arguments, not used.}
}
\description{
print informations about an object of class
\code{\link{pptx}}.
}
\examples{
\donttest{
# Create a new document
doc = pptx( title = "title" )
print( doc )
}
}
\seealso{
\code{\link{pptx}}, \code{\link{print}}
}

